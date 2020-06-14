package com.example.testapplication;

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


    //text
    private String[][] text = {
            {"细胞中的糖类",
                    "说到糖",
                    "我们并不陌生",
                    "除了白糖、冰糖这些我们熟知的糖以外",
                    "淀粉、纤维素等也属于糖类",
                    "这些糖类的分子有什么相同和不同之处呢？淀粉、纤维素并不甜",
                    "为什么也属于糖类呢？ 糖类（carbonydrate）分子都是由碳、氢、氧三种元素构成的",
                    "因为多数糖类分子中氢原子和氧原子之比是2比1"},
            {"类似水分子",
                    "因而糖类又称为碳水化合物",
                    "糖类大致可以分为单糖、二糖和多糖等几类",
                    "单糖",
                    "葡萄糖是细胞生命活动所需要的主要能量物质",
                    "常被形容为生命的燃料",
                    "葡萄糖不能水解",
                    "可直接被细胞吸收"},
            {"像这样不能水解的糖就是单糖",
                    "二糖",
                    "二糖（C十二H二十二O十一）由两分子单糖脱水缩合而成",
                    "二糖必须水解成单糖才能被细胞吸收",
                    "生活中最常见的二糖是蔗糖",
                    "红糖、白糖、冰糖等都是由蔗期加工制成的",
                    "多糖",
                    "生物体内的糖类绝大多数以多糖"},
            {"（C6H十O5）n）的形式存在",
                    "淀粉是最常见的多糖",
                    "淀粉不易溶于水",
                    "人们食用的淀粉",
                    "必须经过消化分解成葡萄糖",
                    "才能被细胞吸收利用",
                    "食物中的淀粉水解后变成葡萄糖",
                    "这些葡萄糖成为人和动物体合成动物多糖——糖原的原料"},
            {"糖原主要分布在人和动物的肝脏和肌肉中",
                    "是人和动物细胞的储能物质",
                    "当细胞生命活动消耗了能量",
                    "人和动物血液中葡萄糖低于正常含量时",
                    "糖原便分解产生葡萄糖及时补充",
                    "你注意过棉花、棕榈和麻类植物吗？它们都有长长的纤维细丝",
                    "还有那些分布在其他植物茎秆和枝叶中的纤维",
                    "以及所有植物细胞的细胞壁"},
            {"构成它们的主要成分都是纤维素",
                    "纤维素也是多糖",
                    "不溶于水",
                    "在人和动物体内很难被消化",
                    "即使草食类动物由发达的消化器官",
                    "也需要借助某些微生物的帮忙才能分解这类多糖",
                    "与淀粉和糖原一样",
                    "纤维素也是由许多葡萄糖连接而成的，构成它们的基本单位都是葡萄糖分子"}
    };

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
