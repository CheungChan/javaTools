package com.cc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

@SuppressWarnings("serial")
public class MyKaptchaServlet extends HttpServlet implements Servlet {
	private Properties props = new Properties();

	private Producer kaptchaProducer = null;

	private String sessionKeyValue = null;

	private String sessionKeyDateValue = null;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);

		// Switch off disk based caching.
		ImageIO.setUseCache(false);

		Enumeration<?> initParams = conf.getInitParameterNames();
		while (initParams.hasMoreElements()) {
			String key = (String) initParams.nextElement();
			String value = conf.getInitParameter(key);
			this.props.put(key, value);
		}

		Config config = new Config(this.props);
		this.kaptchaProducer = config.getProducerImpl();
		this.sessionKeyValue = config.getSessionKey();
		this.sessionKeyDateValue = config.getSessionDate();
	}

	/** */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set to expire far in the past.
		resp.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		resp.setHeader("Pragma", "no-cache");

		// return a jpeg
		resp.setContentType("image/jpeg");

		// create the text for the image
		String capText = this.kaptchaProducer.createText();
		int num1 = Integer.parseInt(capText.substring(0, 2));
		int num2 = Integer.parseInt(capText.substring(2, 4));
		int operator = new Random().nextInt(4);
		int sessionKeyValue = 0;
		String imageString = null;
		switch (operator) {
		case 0:
			sessionKeyValue = num1 + num2;
			imageString = num1 + "+" + num2;
			break;
		case 1:
			if (num1 - num2 < 0) {
				sessionKeyValue = num2 - num1;
				imageString = num2 + "-" + num1;
			} else {
				sessionKeyValue = num1 - num2;
				imageString = num1 + "-" + num2;
			}
			break;
		case 2:
			if (num1 * num2 > 200) {
				if (num1 - num2 < 0) {
					sessionKeyValue = num2 - num1;
					imageString = num2 + "-" + num1;
				} else {
					sessionKeyValue = num1 - num2;
					imageString = num1 + "-" + num2;
				}
			} else {
				sessionKeyValue = num1 * num2;
				imageString = num1 + "*" + num2;
			}
			break;
		case 3:
			if (num2 == 0 || num1 < num2 || num1 % num2 != 0) {
				sessionKeyValue = num1 + num2;
				imageString = num1 + "+" + num2;
			} else {
				sessionKeyValue = num1 / num2;
				imageString = num1 + "/" + num2;
			}
		}
		imageString += "=?";
		// store the text in the session
		req.getSession().setAttribute(this.sessionKeyValue, sessionKeyValue + "");

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(this.sessionKeyDateValue, new Date());

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(imageString);

		ServletOutputStream out = resp.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
	}
}
