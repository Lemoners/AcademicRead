package com.example.prototype2;

import android.util.Log;
import android.widget.SeekBar;

import java.util.Vector;

public class FileLoader {
    // paragraph index
    private int posX = 0;
    // line index
    private int posY = 0;
    // character index
    private int pos_char = 0;
    // all character count
    private int count = 0;
    // bookmarks:
    private Vector<String> book_marks;
    //percentage
    private int my_percentage = 0;


    //for following
    public SeekBar seekBar;

//    private String[][] text = {{
//            "细胞中的脂质","你注意过肉类食品中的肥肉吗","肥肉的主要成分是脂肪","食用植物油是从油料作物中提取的","其主要成分也是脂肪","脂肪是脂质的一种","脂质存在于所有细胞中","是组成细胞和生物体的重要有机化合物","与糖类相似","组成脂质的化学元素主要是碳、氢、氧","有些脂质还含有磷和氮","所不同的是脂质分子中氧的含量远远少于糖类","而氢的含量更多","常见的脂质有脂肪、磷脂、固醇等","它们的分子结构差异很大","通常都不溶于水","而溶于脂溶性有机溶剂","如丙醇、氯仿、乙醚等","脂肪是最常见的脂质","一克糖原氧化分解释放出约十七千焦的能量","而一克脂肪可以放出约三十八千焦的能量","脂肪是细胞内良好的储能物质","当生命活动需要时可以分解利用","脂肪不仅是储能物质","还是一种很好的绝热体","生活在海洋中的大型哺乳动物如鲸、海豹等","皮下有厚厚的脂肪层","起到保温作用","生活在南极寒冷环境中的企鹅","体内脂肪可厚达四厘米","分布在内脏器官周围的脂肪还具有缓冲和减压的作用","可以保护内脏器官"
//    }};

    private String[][] text = {{
            "细胞中的水","人们普遍认为地球上最早的生命是在海洋中孕育的","生命从一开始就离不开水","干燥的种子必须吸足水才能萌发","人的胚胎也要浸润在羊水中发育","沙漠里的仙人掌","身处如此干旱的环境","它那肥硕的变态茎中仍贮存着大量的水分","干旱可以使植物枯萎","人体老化的特征之一是身体细胞的含水量明显下降","水是构成细胞的重要无机化合物","一般地说","水在细胞的各种化学成分中含量最多","生物体的含水量随着生物种类的不同有所差别","一般为百分之六十至百分之九十五","水母的含水量达到97%","生物体在不同的生长发育期","含水量也不同","例如","幼儿身体的含水量远远高于成年人身体的含水量","植物幼嫩部分比老熟部分含水量更多","水在细胞中以两种形式存在","一部分水与细胞内的其他物质相结合","叫做结合水","结合水是细胞结构的重要组成成分","大约占细胞内全部水分的百分之四点五","细胞中绝大部分的水以游离的形式存在","可以自由流动","叫做自由水","自由水是细胞内的良好溶剂","许多物质溶解在这部分水中","细胞内的许多生物化学反应也都需要有水的参与","多细胞生物体的绝大多数细胞","必须浸润在以水为基础的液体环境中","总之","各种生物体的一切生命活动都离不开水"
    }};

    //text
//    private String[][] text = {
//            {"细胞中的糖类",
//                    "说到糖",
//                    "我们并不陌生",
//                    "除了白糖、冰糖这些我们熟知的糖以外",
//                    "淀粉、纤维素等也属于糖类",
//                    "这些糖类的分子有什么相同和不同之处呢？淀粉、纤维素并不甜",
//                    "为什么也属于糖类呢？ 糖类（carbonydrate）分子都是由碳、氢、氧三种元素构成的",
//                    "因为多数糖类分子中氢原子和氧原子之比是2比1"},
//            {"类似水分子",
//                    "因而糖类又称为碳水化合物",
//                    "糖类大致可以分为单糖、二糖和多糖等几类",
//                    "单糖",
//                    "葡萄糖是细胞生命活动所需要的主要能量物质",
//                    "常被形容为生命的燃料",
//                    "葡萄糖不能水解",
//                    "可直接被细胞吸收"},
//            {"像这样不能水解的糖就是单糖",
//                    "二糖",
//                    "二糖（C十二H二十二O十一）由两分子单糖脱水缩合而成",
//                    "二糖必须水解成单糖才能被细胞吸收",
//                    "生活中最常见的二糖是蔗糖",
//                    "红糖、白糖、冰糖等都是由蔗期加工制成的",
//                    "多糖",
//                    "生物体内的糖类绝大多数以多糖"},
//            {"（C6H十O5）n）的形式存在",
//                    "淀粉是最常见的多糖",
//                    "淀粉不易溶于水",
//                    "人们食用的淀粉",
//                    "必须经过消化分解成葡萄糖",
//                    "才能被细胞吸收利用",
//                    "食物中的淀粉水解后变成葡萄糖",
//                    "这些葡萄糖成为人和动物体合成动物多糖——糖原的原料"},
//            {"糖原主要分布在人和动物的肝脏和肌肉中",
//                    "是人和动物细胞的储能物质",
//                    "当细胞生命活动消耗了能量",
//                    "人和动物血液中葡萄糖低于正常含量时",
//                    "糖原便分解产生葡萄糖及时补充",
//                    "你注意过棉花、棕榈和麻类植物吗？它们都有长长的纤维细丝",
//                    "还有那些分布在其他植物茎秆和枝叶中的纤维",
//                    "以及所有植物细胞的细胞壁"},
//            {"构成它们的主要成分都是纤维素",
//                    "纤维素也是多糖",
//                    "不溶于水",
//                    "在人和动物体内很难被消化",
//                    "即使草食类动物由发达的消化器官",
//                    "也需要借助某些微生物的帮忙才能分解这类多糖",
//                    "与淀粉和糖原一样",
//                    "纤维素也是由许多葡萄糖连接而成的，构成它们的基本单位都是葡萄糖分子"}
//    };

    FileLoader() {
        count = 0;
        for(int i = 0; i < text.length; i++) {
            for (int j = 0; j < text[i].length; j++) {
                count += text[i][j].length();
            }
        }
        book_marks = new Vector<String>();
        seekBar = null;
    }

    String get_text() {
        return text[posX][posY];
    }

    String get_cursor_text() {
        return text[posX][posY].substring(pos_char);
    }

    String get_cursor_char() {return text[posX][posY].substring(pos_char, pos_char+1);}

    int get_pos_char() {
        return this.pos_char;
    }

    int get_cur_paragraph_len() {
        return text[posX].length;
    }

    String get_cur_info() {return String.format("当前是第%s章第%s句", String.valueOf(posX), String.valueOf(posY));}

    String add_or_delete_book_marks() {
        String marks = String.valueOf(posX) + " " + String.valueOf(posY);
        boolean in_book_mark = false;
        for (int i = 0; i < book_marks.size(); i++) {
            if (marks.equals(book_marks.elementAt(i))) {
                book_marks.remove(i);
                in_book_mark = true;
                break;
            }
        }
        if (in_book_mark) {
            return "书签已删除";
        } else {
            book_marks.add(marks);
            Log.e("BM", ""+book_marks.size());
            return "已加入书签";
        }
    }

    Vector<String> get_book_marks() {return book_marks;}

    String get_book_marks_info(int index) {
        String[] marks = book_marks.elementAt(index).split(" ");
        String res = String.format("书签%d号: %s", index, text[Integer.parseInt(marks[0])][Integer.parseInt(marks[1])]);
        return res;
    }

    void setSeekBar(SeekBar _seekBar){seekBar = _seekBar;}

    String jump_book_marks(String _marks) {
        String[] marks = _marks.split(" ");
        posX = Integer.parseInt(marks[0]);
        posY = Integer.parseInt(marks[1]);
        pos_char = 0;
        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
        return String.format("已跳转至第%d段第%d句",posX, posY);
    }

    void inner_line_char_switch(int delta) {
        int _delta = Math.abs(delta);
        int _posX = posX, _posY = posY, _pos_char = pos_char;
        if (delta > 0) {
            if (_pos_char == text[_posX][_posY].length() - 1) {
                if (_posX == text.length - 1 && _posY == text[_posX].length - 1) {
                    _pos_char = text[_posX][_posY].length() - 1;
                } else {
                    _pos_char = 0;
                    _posY += 1;
                    if (_posY == text[_posX].length - 1) {
                        _posY = 0;
                        _posX += 1;
                    }
                }
            } else {
                _pos_char += _delta;
                _pos_char = Math.min(_pos_char, text[_posX][_posY].length()-1);
            }
        } else {
            if (_pos_char == 0) {
                if (_posX == 0 && _posY == 0) {
                    _pos_char = 0;
                } else {
                    _posY -= 1;
                    if (_posY == - 1) {
                        _posX -= 1;
                        _posY = text[_posX].length - 1;
                    }
                    _pos_char = text[_posX][_posY].length() - 1;
                }
            } else {
                _pos_char -= _delta;
                _pos_char = Math.max(_pos_char, 0);
            }
        }
        posX = _posX;
        posY = _posY;
        pos_char = _pos_char;

        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
    }

    void line_switch(int delta) {
        int _delta = Math.abs(delta);
        int _posX = posX, _posY = posY, _pos_char = pos_char;
        _pos_char = 0;
        if (delta >= 0) {
            while (_delta > 0) {
                if (text[_posX].length - 1 - _posY >= _delta) {
                    _posY += _delta;
                    break;
                }

                _delta -= (text[_posX].length - 1 - _posY);

                if (_posX == text.length - 1 && _posY == text[_posX].length - 1) {
                    // end of text
                    break;
                }

                _delta -= 1;
                _posY += 1;
                if (_posY == text[_posX].length) {
                    _posY = 0;
                    _posX += 1;
                }
            }
        } else {
            while (_delta > 0) {
                if (_posY >= _delta) {
                    _posY -= _delta;
                    break;
                }

                _delta -= _posY;

                if (_posX == 0 && _posY == 0) {
                    // start of text
                    break;
                }

                _delta -= 1;
                _posY -= 1;
                if (_posY < 0) {
                    _posX -= 1;
                    _posY = text[_posX].length - 1;
                }
            }
        }
        posX = _posX;
        posY = _posY;
        pos_char = _pos_char;
        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
    }

    void char_switch(int delta) {
        int _delta = Math.abs(delta);
        int _posX = posX, _posY = posY, _pos_char = pos_char;

        if (delta >= 0) {
            while (_delta > 0) {
                if (text[_posX][_posY].length() - 1 - _pos_char >= _delta) {
                    _pos_char += _delta;
                    break;
                }

                _delta -= (text[_posX][_posY].length() - 1 - _pos_char);

                if (_posX == text.length - 1 && _posY == text[_posX].length - 1) {
                    // end of text
                    _pos_char = text[_posX][_posY].length() - 1;
                    break;
                }

                _pos_char = 0;
                _delta -= 1;
                _posY += 1;
                if (_posY == text[_posX].length) {
                    _posY = 0;
                    _posX += 1;
                }
            }
        } else {
            while (_delta > 0) {
                if (_pos_char >= _delta) {
                    _pos_char -= _delta;
                    break;
                }

                _delta -= _pos_char;

                if (_posX == 0 && _posY == 0) {
                    // start of text
                    _pos_char = 0;
                    break;
                }

                _delta -= 1;
                _posY -= 1;
                if (_posY < 0) {
                    _posX -= 1;
                    _posY = text[_posX].length - 1;
                }
                _pos_char = text[_posX][_posY].length() - 1;_pos_char = text[_posX][_posY].length() - 1;
            }
        }
        posX = _posX;
        posY = _posY;
        pos_char = _pos_char;
        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
    }

    void setPos_char(int _pos_char) {
        pos_char = _pos_char;
        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
    }

    int get_percentage() {
        int c = 0;
        for (int i = 0; i < posX; i++) {
            for (int j = 0; j < text[i].length; j++) {
                c += text[i][j].length();
            }
        }
        for (int i = 0; i < posY; i++) {
            c += text[posX][i].length();
        }
        return (int)(100*c/count);
    }

    void percentage_switch(int per) {
        if (per == my_percentage) {
            return;
        }
        my_percentage = per;
        int cur = (count*per/100);

        int _posX = 0;
        int _posY = 0;
        int _pos_char = 0;

        while (cur > 0) {
            cur -= text[_posX][_posY].length();
            _posY += 1;
            if(_posY == text[_posX].length && _posX == text.length - 1) {
                _posY -= 1;
                break;
            }

            if(_posY == text[_posX].length) {
                _posY = 0;
                _posX += 1;
            }
        }
        posX = _posX;
        posY = _posY;
        pos_char = _pos_char;

        if (seekBar != null) {
            seekBar.setProgress(get_percentage());
        }
    }
}
