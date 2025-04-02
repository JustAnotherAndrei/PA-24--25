package com.imageapp.commands;

import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.Image;
import com.imageapp.model.ImageCollection;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command {
    private ImageCollection collection;
    private static final String TEMPLATE_DIR = "templates";
    private static final String TEMPLATE_NAME = "report.ftl";

    public ReportCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        try {
            // Set up FreeMarker configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Template template = cfg.getTemplate(TEMPLATE_NAME);

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("images", collection.getImages());

            File output = new File("report.html");
            try (FileWriter writer = new FileWriter(output)) {
                template.process(dataModel, writer);
            }

            // Attempt to open the report automatically
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(output.toURI());
            }
            System.out.println("Report generated successfully: " + output.getAbsolutePath());
        } catch (Exception e) {
            throw new InvalidDataException("Error generating report: " + e.getMessage());
        }
    }
}
