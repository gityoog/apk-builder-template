# apk-builder-template

template for node-apk-builder

## 安装

```
$ git clone https://github.com/gityoog/apk-builder-template.git
```

## 使用

```
$ npm run dev # for debug apk

$ npm run build # for release apk
```

## 配置

```ts
// script/config/index.ts

/** apk输出目录 */
dist: string;

/** 源码目录 */
src: string;

/** android.jar 位置 */
androidJar: string;

/** 是否需要显示打包进程 (default: true) */
render?: boolean;

/** 打包工具位置 如已加入环境变量可以不传 */
buildTools?: string;

/** apk签名文件位置 目前只支持 pk8+pem 后续会增加 */
sign: {
  key: string;
  cert: string;
};

/** 开启adb自动安装自动运行 main为自动启动路径 package/.activity*/
adb?: {
  main: string;
};
```
