Run Force.com Security Source Code Scanner
===========================================
\[[English version](README.md)\]

はじめに
--------
Run Force.com Security Source Code Scanner は、Force.com Security Source Code Scanner を実行するためのカスタムAntタスクです。このツールは、特に Jenkins とともに利用することによりApex開発のソースコードの品質及びセキュリティを向上させることができ、CI(Continuous Integration)の実践を支援します。
Run Force.com Security Source Code Scanner は、次のような機能を実現します。

* Force.com Security Source Code Scanner をAntから実行する

なお、Force.com Security Source Code Scanner については, [Force.com Security Source Code Scanner](http://security.force.com/security/tools/forcecom/scanner)をご覧ください。


インストール
------------

1. Apache Ant(1.9.2)、Selenium Server(2.37.0), Selenium Client & WebDriver Language Bindings Java(2.37.0)をインストールします。
1. `selenium-java-`*version*`-srcs.jar`と`selenium-java-`*version*`.jar`と`ant-runfscscanner.jar`を、Antインストールディレクトリ以下の`lib/`ディレクトリ内にコピーします。
1. Firefoxをインストールします(現在のバージョンは時間切れのためFirefox固定)。

build.xmlの作成
--------------- 
build.xmlを開き、projectルート要素に、**xmlns:runfscscanner="antlib:jp.gr.java_conf.a_kura.ant.runfscscanner"**という属性設定を以下のように追加します。

    <project name="Sample usage of Ant tasks" basedir="." xmlns:runfscscanner="antlib:jp.gr.java_conf.a_kura.ant.runfscscanner">

以下では、タスク定義のサンプルを紹介します。

    <target name="runFscScanner">
      <runfscscanner:runFscScanner
        username="${sf.username}"
        description="Product X"
        scanProfile="Beta Rules (+CRUD/FLS)"
      />
    </target>


スキャン実行
------------
定義したrunFscScannerタスクを含むターゲットをAntから実行してください。
Force.com Security Source Code Scanner のスキャン結果は、Webページから実行した場合と同様にメールで届きます。
メールで届いたスキャン結果を確認してください。


RunFscScanner タスクリファレンス
-------------------------------
runFscScannerタスクは Force.com Security Source Code Scanner を実行するために使用されます。

<dl>
<dt>**username**</dt>
<dd>必須の属性。Force.com Security Source Code Scanner 実行時に入力するユーザー名。</dd>

<dt>**description**</dt>
<dd>任意の属性。Force.com Security Source Code Scanner 実行時に入力する説明。</dd>

<dt>**description**</dt>
<dd>必須の属性。Force.com Security Source Code Scanner 実行時に入力するScanProfile。有効な値は'All Rules', 'Security Rules', 'Quality Rules', 'Beta Rules (+CRUD/FLS)', 'Quality Beta Rules'のいずれか。</dd>
</dl>



