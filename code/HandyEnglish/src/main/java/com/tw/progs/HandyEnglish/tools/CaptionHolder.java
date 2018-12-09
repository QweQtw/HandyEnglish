package com.tw.progs.HandyEnglish.tools;

import com.vaadin.server.ThemeResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by VCLERK on 24.03.2017.
 */
@Component
public class CaptionHolder {

    private final String separator = ";";
    private final String comment = "---===";

    private List<String[]> content = new ArrayList<>();
    private int languageIdx = 0;

    ThemeResource regular = new ThemeResource("../dark/bmp/flag_green.bmp");
    ThemeResource deleted = new ThemeResource("../dark/bmp/flag_red.bmp");
    ThemeResource notAvai = new ThemeResource("../dark/bmp/na.bmp");

    public ThemeResource getRegularIcon() {
        return regular;
    }

    public ThemeResource getDeletedIcon() {
        return deleted;
    }

    public ThemeResource getNotAvailableIcon() {
        return notAvai;
    }


    public CaptionHolder(@Value("${app.language}") String language) {
//        URI filename = this.getClass().getClassLoader().getResource("translations._res").toURI();
//        Path pth = FileSystems.getDefault().getPath(new UrlResource(filename).toString());


//        try (Stream<String> stream = Files.lines(pth)) {

//      String filename = this.getClass().getClassLoader().getResource("translations._res").toString();
//      try (Stream<String> stream = Files.lines(Paths.get(URI.create(filename)), Charset.forName("UTF-8"))) {

//        System.out.print("\n\n---===***************************\n");
//        System.out.print(this.getClass().getClassLoader().getResource("translations._res").toString());
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("translations._res");
//        System.out.print((is==null)?"isr NUll":"isr NOT NULL");
//        System.out.print("\n\n---===***************************");
        InputStreamReader isr = new InputStreamReader(is);
        try(Stream<String> stream = new BufferedReader(isr).lines()){
            stream.forEach(line -> {
                if ((!line.trim().isEmpty())&&(!line.startsWith(comment))) {
                    String[] parts = line.split(separator);
                    content.add(parts);
                }
            }
            );
        }
        setLanguage(language);
    }

    public String[] getLanguages(){
        return content.get(0);
    }

    public String getSelectedLanguage(){return content.get(0)[languageIdx];};

    public void setLanguage(String pattern){
        languageIdx = findBestMatch(getLanguages(), pattern);
    }

    private int findBestMatch(String[] items, String pattern) {
        int result = -1;
        Map<Integer, String> similar = new HashMap<>();

        for (int idx=0; idx<items.length; idx++) {
            if (items[idx].equalsIgnoreCase(pattern)) return idx;
            if (items[idx].toLowerCase().contains(pattern.toLowerCase()))similar.put(idx, items[idx]);
        }

        if(similar.size()==0) return result;

        if (similar.size()==1)
            return similar.keySet().stream().findFirst().get();
        else{

            List<Map.Entry<Integer, String>> lst  =(similar.entrySet().stream()
                    .filter(item -> item.getValue().length()==pattern.length()).limit(1)
                    .collect(Collectors.toList()));
            if (lst.size()==1)
                return lst.get(0).getKey();
            else{
                Comparator <Map.Entry<Integer, String>> cmp = (i1, i2) -> Integer.compare(i1.getValue().length(), i1.getValue().length());
                result = similar.entrySet().stream().min(cmp).get().getKey();
            }
        }

        return result;
    }

    public String getCaption(String pattern){
        String result = "?";
        List<String> tmp = content.stream().map(item-> item[0]).collect(Collectors.toList());
        List<String> res = content.stream().map(item-> item[languageIdx]).collect(Collectors.toList());
        if (tmp.size()>0) {
            int idx = findBestMatch(tmp.toArray(new String[tmp.size()]), pattern);
            if (idx>-1) result = res.get(idx);
        }
        return result;
    }

    public String getShortDescription(String text){
        return getShortDescription(text, 64);
    }
    public String getShortDescription(String text, int length){
        return ((text.length()<length)?text:text.subSequence(0, length-1)+"..." );
    }

}
