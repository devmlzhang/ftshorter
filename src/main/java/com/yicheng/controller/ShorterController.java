package com.yicheng.controller;


import com.yicheng.cache.RedisCache;
import com.yicheng.utils.Helper;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.repo.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ShorterController {

    protected AtomicLong gen = new AtomicLong();

    protected String root ="/shortit/root";

    @Autowired
    RedisCache redisCache;

    @GetMapping("/c/{id}")
    public String code(@PathVariable("id") String code){

        long id = Helper.string2Id(code);
        if (id < 0) {
            return "404";
        }
        String t = render(id);
        if (t == null)
            return "404";
        return t;
    }

    @PostMapping("/api/read/{id}")
    @ResponseBody
    public Object read(@PathVariable("id") String code, HttpServletResponse resp)
            throws FileNotFoundException {
        long id = Helper.string2Id(code);
        if (id < 0)
            return HttpStatusView.HTTP_404;
        File f = new File(root + "/" + idPath(id));
        if (!f.exists())
            return HttpStatusView.HTTP_404;
        if (resp != null) {
            resp.setHeader("Content-Length", "" + f.length());
            resp.setContentType("text/plain; charset=utf8");
        }

        return Files.read(f);
    }

    @GetMapping("/api/down/{id}")
    public Object down(@PathVariable("id") String code, HttpServletResponse resp)
            throws IOException {

        long id = Helper.string2Id(code);

        if (id < 0)
            return HttpStatusView.HTTP_404;
        File f = new File(root + "/" + idPath(id));
        String meta = meta(f);
        if (meta == null || !meta.startsWith("bin:") || meta.length() < 5) {
            return HttpStatusView.HTTP_404;
        }

        String filename = meta.substring(4);
        filename = URLEncoder.encode(filename, Encoding.UTF8);

        resp.setHeader("Content-Length", "" + f.length());
        resp.setHeader("Content-Disposition", "attachment; filename=\""
                + filename + "\"");
        Streams.writeAndClose(resp.getOutputStream(), Streams.fileIn(f));
        return null;
    }

    @PostMapping("/api/create/txt")
    @ResponseBody
    public Object createTxt(HttpServletRequest req) throws IOException {
        int fileSize = req.getContentLength();
        if (fileSize < 1)
            return Helper._fail("err.data_emtry");
        if (fileSize > 1024 * 1024 * 10)
            return Helper._fail("err.file_too_big");
        String re = Helper._ok(write(req.getInputStream(), "txt:"));
        return re;
    }

    @PostMapping("/api/create/file")
    @ResponseBody
    public Object createFile(HttpServletRequest req) throws IOException {
        int fileSize = req.getContentLength();
        if (fileSize < 1)
            return Helper._fail("err.data_emtry");
        if (fileSize > 1024 * 1024 * 10)
            return Helper._fail("err.file_too_big");
        String fileName = req.getHeader("X-File-Name");
        if (Strings.isBlank(fileName))
            fileName = "file.bin";
        else {
            fileName = new String(Base64.decodeFast(fileName), "UTF8");
        }
        return Helper._ok(write(req.getInputStream(), "bin:"+fileName));
    }

    public String render(long id) {
        File f = new File(root + "/" + idPath(id));
        String metaStr = meta(f);
        if (metaStr == null) {
            return null;
        }

        if (metaStr.startsWith("txt:")) {
            return "txt";
        } else {
            return "down";
        }
    }

    public static String idPath(long id) {
        String tmp = String.format("%016X", id);
        String path = tmp.substring(0, 2) + "/" +
                tmp.substring(2,4) + "/" +
                tmp.substring(4, 6) + "/" +
                tmp.substring(6, 8) + "/" +
                tmp.substring(10, 12) + "/" +
                tmp.substring(12, 14) + "/" +
                tmp.substring(14);
        return path;
    }

    public String meta(File f) {
        if (f == null)
            return null;
        File meta = new File(f.getParentFile(), f.getName() + ".meta");
        if (!meta.exists() || meta.length() == 0)
            return null;
        return Files.read(meta);
    }

    public long write(InputStream ins, String meta) {
        long id = next();
        String path = idPath(id);
        Files.write(root + "/" + path, ins);
        Files.write(root + "/" + path + ".meta", meta);
        return id;
    }

    protected long next() {
        long id = gen.getAndIncrement();
        redisCache.putCache("ids",""+id);
        //jedis().hset("ids", "shortit", ""+id);
        return id;
    }

}
