import path from 'path'
import ApkBuilderConfig from "apk-builder/dist/config"

const config: ApkBuilderConfig.Options = {
  src: path.resolve(__dirname, '../../src/main'),
  dist: path.resolve(__dirname, '../../dist'),
  androidJar: path.resolve("D:\\Engpath\\android\\platforms\\android-29\\android.jar"),
  buildTools: path.resolve('D:\\Engpath\\android\\build-tools\\29.0.3'),
  sign: {
    key: path.resolve(__dirname, '../cert/app.pk8'),
    cert: path.resolve(__dirname, '../cert/app.x509.pem')
  },
  /** 如果需要使用adb安装和自动运行 请先连接手机或模拟器
  adb: {
    main: 'com.apkbuilder.template/.MainActivity'
  }
   */
}

export default config