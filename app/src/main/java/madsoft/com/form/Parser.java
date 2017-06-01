package madsoft.com.form;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;

/**
 * Created by Даниил on 01.06.2017.
 */

public class Parser {

    private Document document;
    private LinkedList<String> parserOutput;

     Parser(Document document) {

        this.document = document;
    }

    public LinkedList<String> parseConten() {
        String imageLink;

        parserOutput = new LinkedList<String>();

        Elements elements = document.getAllElements();

        for (Element e : elements) {
            switch (e.tag().toString()) {

                case "p":

                    if (!(e.hasClass("hidden-lg hidden-md col-sm-12 col-xs-12")
                            || e.hasClass("text-center text-main-css")
                            || e.hasClass("text-muted pull-right")))
                        if (!e.text().isEmpty())
                           parserOutput.add(e.text().toString());




                    break;
                case "ul":
                    if (!(e.hasClass("nav navbar-nav")
                            || e.hasClass("nav navbar-nav navbar-right")
                            || e.hasClass("breadcrumb")
                            || e.hasClass("list-unstyled list-inline pull-left")))
                        if (!e.text().isEmpty())
                            parserOutput.add(e.text().toString());

                    break;

                case "img":
                    if (!e.hasAttr("style")) {
                        if (e.attr("src").contains(Assets.ROOT) || e.attr("src").contains("http://") ||  e.attr("src").contains("https://") ) {
                            imageLink = e.attr("src");
                            parserOutput.add(imageLink);

                        } else
                            imageLink = Assets.ROOT + e.attr("src");
                            parserOutput.add(imageLink);
                    }


                    break;

            }

        }

       return parserOutput;
        }
    }


