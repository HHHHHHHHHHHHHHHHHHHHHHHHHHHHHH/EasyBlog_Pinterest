package com.bamboovir.easyblog.markdown;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.web.multipart.MultipartFile;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;


public class MarkdownUtil {
	
	@SuppressWarnings("deprecation")
	public boolean utilPath(String srcMarkdownPath, String destMarkdownPath) throws IOException {
		
		if(!srcMarkdownPath.contains(".md") || !destMarkdownPath.contains(".htm")) {
			System.out.println("error <- file type");
			return false;
		}
		
		MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        File markdownFile = new File(srcMarkdownPath);
        Reader markdownReader = new FileReader(markdownFile);
        Node markdownDocument = parser.parseReader(markdownReader);
        markdownReader.close();
        String markdownHtml = renderer.render(markdownDocument);
		FileUtils.writeStringToFile(new File(destMarkdownPath),markdownHtml);
		return true;
	}
	
@SuppressWarnings("deprecation")
public boolean utilFile(MultipartFile srcFile, String destMarkdownPath) throws IOException {
		
		if(!destMarkdownPath.contains(".htm")) {
			System.out.println("error <- file type");
			return false;
		}
	     
	    byte[] buffer = IOUtils.toByteArray(srcFile.getInputStream());
	    Reader markdownReader = new CharSequenceReader(new String(buffer));
	    
		MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node markdownDocument = parser.parseReader(markdownReader);
        markdownReader.close();
        String markdownHtml = renderer.render(markdownDocument);
		FileUtils.writeStringToFile(new File(destMarkdownPath),markdownHtml);
		return true;
	}
	
	
    public void example() {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("This is *Sparta*");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }
}
