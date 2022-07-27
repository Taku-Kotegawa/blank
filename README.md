# Webアプリとバッチの共存テスト

パラメータに何も指定しなければWebアプリが起動し、-Dspring.profile.active=batchを指定するとジョブが起動する。

Webアプリケーションの起動
~~~
java -jar blank-0.0.1-SNAPSHOT.jar
~~~
バッチ処理の起動
~~~
java -Dspring.profiles.active=batch -Dspring.batch.job.names=JOB_ID -jar target/blank-0.0.1-SNAPSHOT.jar
~~~


## Docker の場合

Webアプリケーションの起動
~~~
docker run blank:0.0.1-SNAPSHOT
~~~

バッチ処理の起動
~~~
docker run -e JAVA_OPTS='-Dspring.profiles.active=batch -Dspring.batch.job.names=JOB_ID' blank:0.0.1-SNAPSHOT
~~~


## Linux(Ubuntu)でspring-boot:build-imageが失敗する場合の対処方法

公式サイトの手順(https://docs.docker.com/engine/install/ubuntu/)でDockerをインストールしたLinux環境にて、spring-boot:build-imageが失敗する。
~~~
[INFO] I/O exception (java.io.IOException) caught when processing request to {}->docker://localhost:2376: com.sun.jna.LastErrorException: [2] そのようなファイルやディレクトリはありません
[INFO] Retrying request to {}->docker://localhost:2376
~~~

複合的な理由で接続できず、この両方を対処しないとビルドが成功しない。

1. dockerデーモンが2376ポートで起動していない
1. spring-boot:build-imageからローカルのdockerに接続できない


### a.dockerデーモンを2376ポートで起動
https://docs.docker.com/engine/install/linux-postinstall/#configure-where-the-docker-daemon-listens-for-connections
を参考に2376ポートで待受する様に設定する

sudo systemctl edit docker.service

~~~
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://127.0.0.1:2376
~~~
$ sudo systemctl daemon-reload
$  sudo systemctl restart docker.service

### b.build-imageから127.0.0.1:2376に接続できる様にする

#### 方法1. 環境変数を設定
export DOCKER_HOST=tcp://127.0.0.1:2376

#### 方法2. pom.xmlを修正
~~~
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<configuration>
				<docker>
					<host>tcp://localhost:2376</host>
				</docker>
			</configuration>
		</plugin>
	</plugins>
</build>
~~~


