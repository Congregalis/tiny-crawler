<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
<!--   <a href="https://github.com/Congregalis/tiny-crawler">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a> -->

<h3 align="center">Tiny-Crawler</h3>

  <p align="center">
    小而实用的 Java 爬虫框架，主要用于学习爬虫框架的运作原理。
    <br />
    <a href="https://github.com/Congregalis/tiny-crawler"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Congregalis/tiny-crawler">View Demo</a>
    ·
    <a href="https://github.com/Congregalis/tiny-crawler/issues">Report Bug</a>
    ·
    <a href="https://github.com/Congregalis/tiny-crawler/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot](https://congregalis.github.io/img/tiny-crawler/crawler_structure.png)

本项目采用如上图所示结构来设计，保证了爬取的完整性。以下这几篇文章可能能让您更了解它。
- [如何实现一个简单的爬虫框架——设计篇](https://congregalis.github.io/2021/11/05/%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84%E7%88%AC%E8%99%AB%E6%A1%86%E6%9E%B6%E2%80%94%E2%80%94%E8%AE%BE%E8%AE%A1%E7%AF%87/)
- [如何实现一个简单的爬虫框架——下载篇](https://congregalis.github.io/2021/11/06/%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84%E7%88%AC%E8%99%AB%E6%A1%86%E6%9E%B6%E2%80%94%E2%80%94%E4%B8%8B%E8%BD%BD%E7%AF%87/)
- [爬虫框架优化——url去重](https://congregalis.github.io/2021/11/14/%E7%88%AC%E8%99%AB%E6%A1%86%E6%9E%B6%E4%BC%98%E5%8C%96%E2%80%94%E2%80%94url%E5%8E%BB%E9%87%8D/)

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* Java
* Maven

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

以下步骤帮助你在本地安装并运行本项目。

### Prerequisites

* java
* maven

### Installation

克隆该仓库
```sh
git clone https://github.com/github_username/repo_name.git
```

<!-- USAGE EXAMPLES -->
## Usage

爬取[我博客](https://congregalis.github.io/)中的所有文章对应的标题和正文示例如下：
```java
// 种子url
String seed = "https://congregalis.github.io/";
// 匹配任何“年月日”形式的日期，连接符可以没有或是 . / - 之一
String dateRegex = "(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))";

Crawler.build().addSeed(seed).addRule("https://congregalis.github.io/" + dateRegex + "/.*/").run();
```

<!-- _For more examples, please refer to the [Documentation](https://example.com)_ -->

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

Building...
<!-- - [] Feature 1
- [] Feature 2
- [] Feature 3
    - [] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues). -->

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

贡献使开源社区成为学习、启发和创造的绝佳场所。 **非常感谢您做出的任何贡献**。

如果您有更好的建议，请 fork 存储库并创建拉取请求。您也可以简单地打开带有 “enhancement” 标签的 issue。

别忘了给项目打星星！谢谢您！

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Congregalis - 2056843452@qq.com

Project Link: [https://github.com/Congregalis/tiny-crawler](https://github.com/Congregalis/tiny-crawler)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [WebMagic](https://github.com/code4craft/webmagic)
* [ScriptSpider](https://github.com/xjtushilei/ScriptSpider)
* [Best-README-Template](https://github.com/othneildrew/Best-README-Template)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Congregalis/tiny-crawler.svg?style=for-the-badge
[contributors-url]: https://github.com/Congregalis/tiny-crawler/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Congregalis/tiny-crawler.svg?style=for-the-badge
[forks-url]: https://github.com/Congregalis/tiny-crawler/network/members
[stars-shield]: https://img.shields.io/github/stars/Congregalis/tiny-crawler.svg?style=for-the-badge
[stars-url]: https://github.com/Congregalis/tiny-crawler/stargazers
[issues-shield]: https://img.shields.io/github/issues/Congregalis/tiny-crawler.svg?style=for-the-badge
[issues-url]: https://github.com/Congregalis/tiny-crawler/issues
[license-shield]: https://img.shields.io/github/license/Congregalis/tiny-crawler.svg?style=for-the-badge
[license-url]: https://github.com/Congregalis/tiny-crawler/blob/master/LICENSE.txt
<!-- [linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png -->
