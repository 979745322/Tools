# 有关在Android Studio中使用CMake的更多信息，请阅读
# 文档：https：//d.android.com/studio/projects/add-native-code.html

# 设置构建本地库所需的CMake的最低版本。

cmake_minimum_required(VERSION 3.4.1)

# 创建并命名一个库，将其设置为STATIC
# 或SHARED，并提供相对路径来源代码。
# 你可以定义多个库，CMake为你构建它们。
# Gradle会自动将您的APK与共享库打包。

add_library( # 设置库的名称。
             native-lib

             # 将库设置为共享库。
             SHARED

             # 提供源文件的相对路径。
             src/main/cpp/native-lib.cpp )

# 搜索指定的预建库，并将路径存储为
# 变量。 因为CMake在搜索路径中包含系统库
# 默认情况下，您只需要指定公共NDK库的名称
# 你想添加。 CMake验证之前存在的库
# 完成它的构建。

find_library( # 设置路径变量的名称。
              log-lib

              # 指定NDK库的名称
              # 你想CMake找到。
              log )

# 指定库CMake应链接到您的目标库。 您
# 可以链接多个库，比如你在这个库中定义的库
# 构建脚本，预建的第三方库或系统库。

target_link_libraries( # 指定目标库。
                       native-lib

                       # 将目标库链接到日志库
                       # 包含在NDK中。
                       ${log-lib} )