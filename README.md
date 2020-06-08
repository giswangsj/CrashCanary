# CrashCanary

[![](https://jitpack.io/v/giswangsj/CrashCanary.svg)](https://jitpack.io/#giswangsj/CrashCanary)[![GitHub license](https://img.shields.io/github/license/giswangsj/CrashCanary.svg)](http://www.apache.org/licenses/LICENSE-2.0)

[English](./README_EN.md)

CrashCanary是一个无侵入的安卓崩溃日志记录库，对你的代码没有任务侵入性，无需申请权限，只需要添加依赖，即可在程序崩溃时记录崩溃日志并可查看所有日志。

## Preview

![preview](https://wangsj.oss-cn-shanghai.aliyuncs.com/img/crash_canary.gif)

## Usage

**Step 1.** Add the JitPack repository to your build file

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```groovy
debugImplementation  'com.github.giswangsj:CrashCanary:1.0.0'
```

