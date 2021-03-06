## 可能的交互方式





|      | 单指滑动 | 单击 | 双击 | 双指滑动 | 三击 | 语音输入 | 手势识别 |
| :---- | :----: | :----: | :----: | :----: | :----: | ------ | ------ |
| 分类 | 上下左右，滑动区域 | 点击区域 | 点击区域，时间间隔 | 上下左右，滑动区域 | / | / | / |
| 优点 | 简单 | 简单<br />可以利用持握手机边缘的手指 | 误触概率低 | 误触概率低 | 可以利用位于手机背部的手指敲击 | 简单 | / |
| 缺点 | 误触、距离阈值判定 | 误触 | 识别准确性 | 无法单手操作 | 震动识别对干扰较为敏感 | 未改进传统阅读方式，识别准确性，环境要求高 | 识别的准确性 |

## 需求表格

### 基于绝对空间的实现（类似点显器）

| 需求                    | 频率 | 对应交互方式     | 可能存在的问题                             |
| ----------------------- | ---- | ---------------- | ------------------------------------------ |
| 上一句/下一句           | 高   | 单指触摸         | 无法准确对应到相应行                       |
| 上若干词/下若干词       | 高   | 单指触摸         | 识别的准确性，回溯准确性（如何提示）       |
| 上一页/下一页           | 高   | 双指滑动         | 无法单手操作                               |
| 长距离跳转（上一章/段） | 中   | 滑动条           | 滑动条的位置、精确度、实时反馈的高效性     |
| 自动播放/暂停           | 高   | 特定区域单击     | 特定区域的位置和大小，如何能被用户精确定位 |
| 播放语速调节            | 中   | 特定区域单指滑动 |                                            |

其他需求：书签 、 字典功能

### 基于相对空间的实现（上一次的实现）

| 需求                    | 频率 | 对应交互方式                                                 | 可能存在的问题                                         |
| --------------- | ---- | ------------------------------------------------------------ | ------------------------------------------------------ |
| 上一句/下一句           | 高   | 双指上下滑动                                                 | 无法单手操作                                          |
| 上若干词/下若干词       | 高   | 双指左右滑动                                                 | 回溯的准确性，滑动距离到回溯距离的映射                 |
| 长距离跳转（上一章/段） | 中   | 滑动条                                                       | 滑动条的位置、精确度、实时反馈的高效性                 |
| 自动播放/暂停           | 高 | 长按后手指滑动                                               | 如何设置识别区域大小<br />手指始终长按用户体验可能不好 |
| 增加/删除书签           | 中   | 单指下滑                                                     |                                                        |
| 跳转至书签              | 中   | 单指上滑进入书签模式<br />单指左右滑动选择书签<br />单击跳转 | 用户学习成本 |

## 其他思考：

- 用户的阅读方式为”听觉“，因此常常提示音会打断用户思考，可否使用”其他感官“提供提示效果？
  - `click`的声音作为反馈？
- 考虑**分级目录**
  - 增加分级目录功能（类似树形结构），譬如增加以**章、页**为颗粒度的目录，用户可以自由选择进入哪一章中的哪一页
- 考虑基于绝对空间的实现：

| 问题                                                 | 可能的解决方法                                               |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| 识别精度问题，字如果范围过大，则一个页面显示内容过少 | 只有用户按下时字的位置需要识别，之后根据用户手**移动的距离**（而非手指所处位置）判定用户移到了哪里 |
| 盲按不易选中目标行                                   | 1. 手机壳上增加标识物<br />2. 若未按到文字行则震动提示       |
| 基于空间的实现，跳转困难                             | 可以在页面下方添加滑动轴，同时支持读出当前页码               |

- 交互模式缺少创新性