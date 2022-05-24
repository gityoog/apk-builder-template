import ApkBuilder from 'apk-builder'
import config from './config'

export default class Main {
  builder = ApkBuilder.Create(config)

  build() {
    return this.builder.build()
  }

  watch() {
    return this.builder.watch()
  }

}

const main = new Main()

if (process.argv[2] === 'dev') {
  main.watch()
} else {
  main.build()
}

// console刷新 log-update
// console缩略 cli-truncate
// console颜色 colorette chalk
// console列宽 wrap-ansi
// console边框 boxen
// console状态 log-symbols
// console加载 cli-spinners
