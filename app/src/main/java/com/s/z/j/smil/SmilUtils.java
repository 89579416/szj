package com.s.z.j.smil;

import com.s.z.j.utils.L;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-21.
 */
public class SmilUtils {

    private static Smil smil;
    private static Layout my_layout;
    private static Seq my_seq;
    private static Par my_par;
    private static List<Img> my_imgList;
    /**
     * 需要顺序播放的视频集合
     */
    private static List<Video> my_videoList;
    /**
     * 需要顺序播放的文字集合
     */
    private static List<SmilText> my_smilTextList;


    public static Smil getXml(InputStream stream) {
        smil = new Smil();
        my_layout = new Layout();
        my_seq = new Seq();
        my_par = new Par();
        try {
            SAXReader reader = new SAXReader();
            org.dom4j.Document document = null;
            document = reader.read(stream);
            Element root = document.getRootElement();
            /** 第一部分 head-->layout-->root-layout + region */
            Element head = root.element("head");
            Element layout = head.element("layout");
            Element root_layout = layout.element("root-layout");
            RootLayout rootLayout = new RootLayout();
            try {
                String root_width = root_layout.attribute("width").getValue();
                L.i("root_width=" + root_width);
                rootLayout.setWidth(Integer.valueOf(root_width));
            } catch (Exception e) {
            }
            try {
                String root_height = root_layout.attribute("height").getValue();
                L.i("root_height=" + root_height);
                rootLayout.setHeight(Integer.valueOf(root_height));
            } catch (Exception e) {
            }
            my_layout.setRootLayout(rootLayout);

            List<Element> regions = layout.elements();
            if (regions != null && regions.size() > 0) {
                List<Region> my_regionList = new ArrayList<Region>();
                for (int i = 0; i < regions.size(); i++) {
                    Element element = regions.get(i);
                    Region my_region = new Region();
                    /** BUG1 只保存region名字的    --
                     * 1.seq.dur 值不对
                     * 2.imgList。size == 0
                     * 3.转秒好像都不对
                     *
                     *
                     *
                     * */
                    if ("region".equals(element.getName())) {
                        try {
                            String region_xmlId = element.attribute("id").getValue();
                            L.i("region_xmlId=" + region_xmlId);
                            my_region.setId(region_xmlId);
                        } catch (Exception e) {
                        }
                        try {
                            String region_top = element.attribute("top").getValue();
                            L.i("region_top=" + region_top);
                            my_region.setTop(region_top);
                        } catch (Exception e) {
                        }
                        try {
                            String region_left = element.attribute("left").getValue();
                            L.i("region_left=" + region_left);
                            my_region.setLeft(region_left);
                        } catch (Exception e) {
                        }
                        try {
                            String region_width = element.attribute("width").getValue();
                            L.i("region_width=" + region_width);
                            my_region.setWidth(region_width);
                        } catch (Exception e) {
                        }
                        try {
                            String region_height = element.attribute("height").getValue();
                            L.i("region_height=" + region_height);
                            my_region.setHeight(region_height);
                        } catch (Exception e) {
                        }
                        try {
                            String region_textMode = element.attribute("textMode").getValue();
                            L.i("region_textMode=" + region_textMode);
                            my_region.setTextMode(region_textMode);
                        } catch (Exception e) {
                        }
                        try {
                            String region_textRate = element.attribute("textRate").getValue();
                            L.i("region_textRate=" + region_textRate);
                            my_region.setTextRate(region_textRate);
                        } catch (Exception e) {
                        }
                        try {
                            String region_z_index = element.attribute("z-index").getValue();
                            L.i("region_z_index=" + region_z_index);
                            my_region.setZ_index(Integer.valueOf(region_z_index));
                        } catch (Exception e) {
                        }
                        try {
                            String region_fit = element.attribute("fit").getValue();
                            L.i("region_fit=" + region_fit);
                            my_region.setFit(region_fit);
                        } catch (Exception e) {
                        }
                        my_regionList.add(my_region);
                    }

                }
                my_layout.setRegionList(my_regionList);
            }

            /** 第二部分 body-->seq + par + img  */
            Element body = root.element("body");
            Element seq = body.element("seq");//顺序显示其内部资源
            try {
                String seq_dur = seq.attribute("dur").getValue();
                L.i("秒数：：：：：seq_dur=" + stringToInt(seq_dur));
                my_seq.setDur(stringToInt(seq_dur));
            } catch (Exception e) {

            }
            try {
                String repeatCount = seq.attributeValue("repeatCount");
                L.i("repeatCount=" + repeatCount);
                my_seq.setRepeatCount(repeatCount);
            } catch (Exception e) {
            }

            Element par = seq.element("par");
            try {
                String par_dur = par.attributeValue("dur");
                L.i("par_dur = " + par_dur);
                my_par.setDur(stringToInt(par_dur));
            } catch (Exception e) {
            }


            L.i("我从这里开始做测试啦");

            L.i("测试结束啦");
            /**     seq-->par  同时显示其包含的资源  文字+视频+图片集合 */
            L.i("开始解析 seq-->par里面的内容");
            List<Element> parList = par.elements();
            if (parList != null && parList.size() > 0) {
                my_smilTextList = new ArrayList<>();
                my_imgList = new ArrayList<>();
                my_videoList = new ArrayList<>();
                for (int i = 0; i < parList.size(); i++) {
                    Element smilText = parList.get(i);
                    SmilText my_smilText = new SmilText();
                    //文字
                    if ("smilText".equals(smilText.getName())) {
                        L.i("------------->" + smilText.getStringValue());
                        my_smilText.setText(smilText.getStringValue());
                        try {
                            String smilText_smiltextid = smilText.attributeValue("id");
                            L.i("smilText_smiltextid=" + smilText_smiltextid);
                            my_smilText.setId(smilText_smiltextid);
                        } catch (Exception e) {
                        }
                        try {
                            String smilText_region = smilText.attributeValue("region");
                            L.i("smilText_region=" + smilText_region);
                            my_smilText.setRegion(smilText_region);
                        } catch (Exception e) {
                        }
                        try {
                            String smilText_textAlign = smilText.attributeValue("textAlign");
                            L.i("smilText_textAlign=" + smilText_textAlign);
                            my_smilText.setTextAlign(smilText_textAlign);
                        } catch (Exception e) {
                        }
                        try {
                            String smilText_repeatCount = smilText.attributeValue("repeatCount");
                            L.i("smilText_repeatCount=" + smilText_repeatCount);
                            my_smilText.setRepeatCount(smilText_repeatCount);
                        } catch (Exception e) {
                        }
                        try {
                            String smilText_style = smilText.attributeValue("style");//color: rgb(255, 0, 0);font-size: 20px;
                            L.i("smilText_style=" + smilText_style);
                            my_smilText.setStyle(smilText_style);
                        } catch (Exception e) {
                        }
                        try {
                            Element smilText_tev = smilText.element("tev");
                            try {
                                String tev_begin = smilText_tev.attributeValue("begin");
                                L.i("tev_begin=" + tev_begin);
                                Tev my_tev = new Tev();
                                my_tev.setBegin(stringToInt(tev_begin));
                                my_smilText.setTev(my_tev);
                            } catch (Exception e) {
                            }
                        } catch (Exception e) {
                        }
                        my_smilTextList.add(my_smilText);
                    } else if ("img".equals(smilText.getName())) {
                        Img my_img = new Img();
                        //图片
                        L.i("------------->" + smilText.getName());
                        Element par_img = parList.get(i);
                        try {
                            String par_img_src = par_img.attributeValue("src");
                            L.i("par_img_src=" + par_img_src);
                            my_img.setSrc(par_img_src);
                        } catch (Exception e) {
                        }
                        try {
                            String par_img_region = par_img.attributeValue("region");
                            L.i("par_img_region=" + par_img_region);
                            my_img.setRegion(par_img_region);
                        } catch (Exception e) {
                        }
                        my_imgList.add(my_img);
                    } else if ("video".equals(smilText.getName())) {
                        Video my_video = new Video();
                        //视频
                        L.i("------------->" + smilText.getName());
                        Element par_video = parList.get(i);
                        try {
                            String par_video_src = par_video.attributeValue("src");
                            L.i("par_video_src=" + par_video_src);
                            my_video.setSrc(par_video_src);
                        } catch (Exception e) {
                        }
                        try {
                            String par_video_region = par_video.attributeValue("region");
                            L.i("par_video_region=" + par_video_region);
                            my_video.setRegion(par_video_region);
                        } catch (Exception e) {
                        }
                        my_videoList.add(my_video);
                    } else {
                        L.i("图片视频文字以外的资源？");
                    }
                }
                my_par.setImgList(my_imgList);
                my_par.setVideoList(my_videoList);
                my_par.setSmilTextList(my_smilTextList);
            }
            my_seq.setPar(my_par);
            /** seq-->img */

            L.i("开始解析 seq-->par以外的内容");
            List<Element> seqList = seq.elements();
            if (seqList != null && seqList.size() > 0) {
                List my_seq_smilTextList = new ArrayList<>();
                List my_seq_imgList = new ArrayList<>();
                List my_seq_videoList = new ArrayList<>();
                L.i("seq 外面总共有数据=="+seqList.size());
                for (int i = 0; i < seqList.size(); i++) {
                    Element seqElement = seqList.get(i);
                    L.i("seqElement.name="+seqElement.getName());
                    if (!"par".equals(seqElement.getName())) {
                        L.i("名字不是par的");
                        //文字
                        SmilText my_seq_smiltext = new SmilText();
                        if ("smilText".equals(seqElement.getName())) {
                            L.i("------smilText------->" + seqElement.getStringValue());
                            try {
                                String smilText_smiltextid = seqElement.attributeValue("id");
                                L.i("smilText_smiltextid=" + smilText_smiltextid);
                                my_seq_smiltext.setId(smilText_smiltextid);
                            } catch (Exception e) {
                            }
                            try {
                                String smilText_region = seqElement.attributeValue("region");
                                L.i("smilText_region=" + smilText_region);
                                my_seq_smiltext.setRegion(smilText_region);

                            } catch (Exception e) {
                            }
                            try {
                                String smilText_textAlign = seqElement.attributeValue("textAlign");
                                L.i("smilText_textAlign=" + smilText_textAlign);
                                my_seq_smiltext.setTextAlign(smilText_textAlign);
                            } catch (Exception e) {
                            }
                            try {
                                String smilText_repeatCount = seqElement.attributeValue("repeatCount");
                                L.i("smilText_repeatCount=" + smilText_repeatCount);
                                my_seq_smiltext.setRepeatCount(smilText_repeatCount);
                            } catch (Exception e) {
                            }
                            try {
                                String smilText_style = seqElement.attributeValue("style");//color: rgb(255, 0, 0);font-size: 20px;
                                L.i("smilText_style=" + smilText_style);
                                my_seq_smiltext.setStyle(smilText_style);
                            } catch (Exception e) {
                            }
                            try {
                                Element smilText_tev = seqElement.element("tev");
                                Tev tev = new Tev();
                                try {
                                    String tev_begin = smilText_tev.attributeValue("begin");
                                    L.i("tev_begin=" + tev_begin);
                                    tev.setBegin(stringToInt(tev_begin));
                                    my_seq_smiltext.setTev(tev);
                                } catch (Exception e) {
                                }
                            } catch (Exception e) {
                            }
                            my_seq_smilTextList.add(my_seq_smiltext);
                        } else if ("img".equals(seqElement.getName())) {
                            Img my_seq_img = new Img();
                            //图片
                            L.i("-----img-------->" + seqElement.getName());
                            Element par_img = parList.get(i);
                            try {
                                String par_img_src = par_img.attributeValue("src");
                                L.i("par_img_src=" + par_img_src);
                                my_seq_img.setSrc(par_img_src);
                            } catch (Exception e) {
                            }
                            try {
                                String par_img_region = par_img.attributeValue("region");
                                L.i("par_img_region=" + par_img_region);
                                my_seq_img.setRegion(par_img_region);
                            } catch (Exception e) {
                            }
                            my_seq_imgList.add(my_seq_img);
                        } else if ("video".equals(seqElement.getName())) {
                            Video my_seq_video = new Video();
                            //视频
                            L.i("------video------->" + seqElement.getName());
                            Element par_video = parList.get(i);
                            try {
                                String par_video_src = par_video.attributeValue("src");
                                L.i("par_video_src=" + par_video_src);
                                my_seq_video.setSrc(par_video_src);
                            } catch (Exception e) {
                            }
                            try {
                                String par_video_region = par_video.attributeValue("region");
                                L.i("par_video_region=" + par_video_region);
                                my_seq_video.setRegion(par_video_region);
                            } catch (Exception e) {
                            }
                            my_seq_videoList.add(my_seq_video);
                        } else {
                            L.i("图片视频文字以外的资源？");
                        }
                    }else {
                        L.i("是par的？");
                    }
                }
                my_seq.setImgList(my_seq_imgList);
                my_seq.setVideoList(my_seq_videoList);
                my_seq.setSmilTextList(my_seq_smilTextList);

            }
            smil.setLayout(my_layout);
            smil.setSeq(my_seq);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return smil;
    }

    /**
     * 将 25S  这样的秒数转换为 25
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        String s = "";
        int a = 1;
        if (str.contains("s")) {
            L.i("包含s");
            s = str.substring(0, str.length() - 1);
        } else {
            L.i("不包含s");
            s = str;
        }
        try {
            L.i("转之前打印一下s="+s);
            a = Integer.valueOf(s);
        } catch (Exception e) {
        }

        return a;
    }
}
