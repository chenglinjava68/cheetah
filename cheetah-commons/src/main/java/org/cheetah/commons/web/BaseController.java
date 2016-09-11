package org.cheetah.commons.web;

import com.google.common.io.ByteStreams;
import org.cheetah.commons.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * Created by Max on 2016/5/1.
 */
public class BaseController {

    /**
     * 兼用一切浏览器
     *
     * @param request
     * @param response
     * @param contentType
     * @param fileName
     * @throws IOException
     */
    protected void downloadSetting(HttpServletRequest request, HttpServletResponse response, String contentType, String fileName)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        if (StringUtils.isNotBlank(contentType)) {
            response.setContentType(contentType);
        }
        String enFileName = formatName(request, fileName);
        response.setHeader("Content-disposition", String.format("attachment; filename=%s", enFileName));
    }

    protected void toMedia(HttpServletRequest request, HttpServletResponse response, String filename, String contentType, InputStream inputStream) throws IOException {
        OutputStream toClient = null;
        try {
            downloadSetting(request, response, contentType, filename);
            toClient = new BufferedOutputStream(response.getOutputStream());
            ByteStreams.copy(inputStream, toClient);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toClient.flush();
            toClient.close();
        }
    }

    protected void toMedia(HttpServletRequest request, HttpServletResponse response, String filename, String contentType) {
        try {
            downloadSetting(request, response, contentType, filename);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatName(HttpServletRequest request, String fileName) {
        try {
            if (request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT") > 0) {
                return URLEncoder.encode(fileName, "UTF-8");
            } else {
                return new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void toMedia(String contentType, byte[] bytes, HttpServletResponse response) {
        response.setContentType(contentType + ";charset=UTF-8");
        response.setContentLength(bytes.length);
        try {
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String decode(String name) {
        try {
            return Objects.isNull(name) ? null : URLDecoder.decode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
