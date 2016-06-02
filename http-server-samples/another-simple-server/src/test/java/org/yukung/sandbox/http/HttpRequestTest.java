package org.yukung.sandbox.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.yukung.sandbox.http.Constant.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author Yusuke Ikeda
 */
public class HttpRequestTest {

    @Test
    public void chunk() throws Exception {
        String header =
            "POST /chunk.txt HTTP/1.1" + CRLF
                + "User-Agent: curl/7.37.1" + CRLF
                + "Host: localhost" + CRLF
                + "Accept: */*" + CRLF
                + "Transfer-Encoding: chunked" + CRLF
                + "Expect: 100-continue" + CRLF;

        String body =
            "5" + CRLF
                + "start" + CRLF
                + "c" + CRLF
                + "123456789" + CRLF
                + "0" + CRLF
                + "3" + CRLF
                + "end" + CRLF
                + "0" + CRLF
                + CRLF;

        String httpRequestMessage = header + CRLF + body;

        InputStream in = new ByteArrayInputStream(httpRequestMessage.getBytes());
        HttpRequest request = new HttpRequest(in);

        String headerText = request.getHeaderText();
        String bodyText = request.getBodyText();

        assertThat(headerText, is(header));
        assertThat(bodyText, is("start123456789" + CRLF + "0end"));
    }

    @Test
    public void contentLength() {
        String header =
            "POST / HTTP/1.1" + CRLF
                + "User-Agent: curl/7.37.1" + CRLF
                + "Host: localhost" + CRLF
                + "Accept: */*" + CRLF
                + "Content-Length: 14" + CRLF
                + "Content-Type: application/x-www-form-urlencoded" + CRLF;

        String body = "Message Body!!";

        String httpRequestMessage = header + CRLF + body;

        InputStream in = new ByteArrayInputStream(httpRequestMessage.getBytes());
        HttpRequest request = new HttpRequest(in);

        String headerText = request.getHeaderText();
        String bodyText = request.getBodyText();

        assertThat(headerText, is(header));
        assertThat(bodyText, is(body));
    }

    @Test
    public void contentLength_日本語() {
        String header =
            "POST / HTTP/1.1" + CRLF
                + "User-Agent: curl/7.37.1" + CRLF
                + "Host: localhost" + CRLF
                + "Accept: */*" + CRLF
                + "Content-Length: 15" + CRLF
                + "Content-Type: application/x-www-form-urlencoded" + CRLF;

        String body = "あいうえお";

        String httpRequestMessage = header + CRLF + body;

        InputStream in = new ByteArrayInputStream(httpRequestMessage.getBytes());
        HttpRequest request = new HttpRequest(in);

        String headerText = request.getHeaderText();
        String bodyText = request.getBodyText();

        assertThat(headerText, is(header));
        assertThat(bodyText, is(body));
    }
}