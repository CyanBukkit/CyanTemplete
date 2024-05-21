#!/bin/sh

#
# 版权所有 © 2015-2021 原始作者。
#
# 根据 Apache 许可证 2.0 版（"许可证"）获得许可；
# 除非遵守许可证，否则您不能使用此文件。
# 您可以在以下网址获得许可证副本：
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# 除非适用法律要求或书面同意，否则根据许可证分发的软件
# 按"原样"分发，不附带任何明示或暗示的保证或条件。
# 请参阅许可证，了解有关权限和限制的具体语言。
#

##############################################################################
#
#  由 Gradle 生成的 POSIX 启动脚本。
#
#  运行重要事项：
#
#   (1) 您需要一个符合 POSIX 的 shell 来运行此脚本。如果您的 /bin/sh 不符合要求，
#       但您有其他符合要求的 shell，如 ksh 或 bash，那么运行此脚本，可以输入：
#
#           ksh Gradle
#
#       Busybox 和类似的简化 shell 将无法工作，因为此脚本需要所有这些 POSIX shell 特性：
#         * 函数；
#         * 展开 «$var», «${var}», «${var:-default}», «${var+SET}», «${var#prefix}», «${var%suffix}», 和 «$( cmd )»；
#         * 具有可测试退出状态的复合命令，特别是 «case»；
#         * 各种内建命令，包括 «command», «set», 和 «ulimit»。
#
#  修补重要事项：
#
#   (2) 此脚本针对任何 POSIX shell，因此避免使用 Bash、Ksh 等提供的扩展；
#       特别是避免使用数组。
#
#       将多个参数打包到一个由空格分隔的字符串中是众所周知的错误和安全问题的来源，
#       因此这（主要）被避免，通过逐步累积选项到 "$@"，最终将其传递给 Java。
#
#       当继承的环境变量（DEFAULT_JVM_OPTS, JAVA_OPTS, 和 GRADLE_OPTS）依赖于单词分割时，
#       这将明确执行；详见内联注释。
#
#       对于特定操作系统的调整，如 AIX、CygWin、Darwin、MinGW 和 NonStop。
#
#   (3) 此脚本是从 Groovy 模板
#       https://github.com/gradle/gradle/blob/HEAD/subprojects/plugins/src/main/resources/org/gradle/api/internal/plugins/unixStartScript.txt
#       在 Gradle 项目中生成的。
#
#       您可以在 https://github.com/gradle/gradle/. 找到 Gradle。
#
##############################################################################

#
# 尝试设置 APP_HOME
#
# 解析链接：$0 可能是一个链接
app_path=$0

# 需要这个来处理连续的符号链接。
while
    APP_HOME=${app_path%"${app_path##*/}"}  # 留下一个尾随的 /；如果没有前导路径则为空
    [ -h "$app_path" ]
do
    ls=$( ls -ld "$app_path" )
    link=${ls#*' -> '}
    case $link in             #(
      /*)   app_path=$link ;; #(
      *)    app_path=$APP_HOME$link ;;
    esac
done

# 这通常未使用
# shellcheck disable=SC2034
APP_BASE_NAME=${0##*/}
# 如果设置了 $CDPATH，则丢弃 cd 标准输出（详情见 https://github.com/gradle/gradle/issues/25036）
APP_HOME=$( cd "${APP_HOME:-./}" > /dev/null && pwd -P ) || exit

# 使用最大可用值，或者设置 MAX_FD != -1 来使用该值。
MAX_FD=maximum

warn () {
    echo "$*"
} >&2

die () {
    echo
    echo "$*"
    echo
    exit 1
} >&2
# 操作系统特定的支持（必须为 'true' 或 'false'）。
cygwin=false
msys=false
darwin=false
nonstop=false
case "$( uname )" in                #(
  CYGWIN* )         cygwin=true  ;; #(
  Darwin* )         darwin=true  ;; #(
  MSYS* | MINGW* )  msys=true    ;; #(
  NONSTOP* )        nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# 确定启动 JVM 时使用的 Java 命令。
if [ -n "$JAVA_HOME" ]; then
    if [ -x "$JAVA_HOME/jre/sh/java" ]; then
        # IBM 的 JDK 在 AIX 上使用奇怪的路径来存放可执行文件
        JAVACMD=$JAVA_HOME/jre/sh/java
    else
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ]; then
        die "ERROR: JAVA_HOME 设置到了一个无效的目录：$JAVA_HOME

请将 JAVA_HOME 环境变量设置为您的 Java 安装位置。"
    fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1; then
        die "ERROR: JAVA_HOME 未设置，且在您的 PATH 中未找到 'java' 命令。

请将 JAVA_HOME 环境变量设置为您的 Java 安装位置。"
    fi
fi

# 如果可以的话，增加最大文件描述符的数量。
if ! "$cygwin" && ! "$darwin" && ! "$nonstop" ; then
    case $MAX_FD in #(
      max*)
        # 在 POSIX sh 中，ulimit -H 是未定义的。这就是为什么需要检查结果是否成功。
        # shellcheck disable=SC2039,SC3045
        MAX_FD=$( ulimit -H -n ) ||
            warn "无法查询最大文件描述符限制"
    esac
    case $MAX_FD in  #(
      '' | soft) :;; #(
      *)
        # 在 POSIX sh 中，ulimit -n 是未定义的。这就是为什么需要检查结果是否成功。
        # shellcheck disable=SC2039,SC3045
        ulimit -n "$MAX_FD" ||
            warn "无法将最大文件描述符限制设置为 $MAX_FD"
    esac
fi

# Collect all arguments for the java command, stacking in reverse order:
#   * args from the command line
#   * the main class name
#   * -classpath
#   * -D...appname settings
#   * --module-path (only if needed)
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, and GRADLE_OPTS environment variables.

# For Cygwin or MSYS, switch paths to Windows format before running java
if "$cygwin" || "$msys" ; then
    APP_HOME=$( cygpath --path --mixed "$APP_HOME" )
    CLASSPATH=$( cygpath --path --mixed "$CLASSPATH" )

    JAVACMD=$( cygpath --unix "$JAVACMD" )

    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    for arg do
        if
            case $arg in                                #(
              -*)   false ;;                            # don't mess with options #(
              /?*)  t=${arg#/} t=/${t%%/*}              # looks like a POSIX filepath
                    [ -e "$t" ] ;;                      #(
              *)    false ;;
            esac
        then
            arg=$( cygpath --path --ignore --mixed "$arg" )
        fi
        # Roll the args list around exactly as many times as the number of
        # args, so each arg winds up back in the position where it started, but
        # possibly modified.
        #
        # NB: a `for` loop captures its iteration list before it begins, so
        # changing the positional parameters here affects neither the number of
        # iterations, nor the values presented in `arg`.
        shift                   # remove old arg
        set -- "$@" "$arg"      # push replacement arg
    done
fi


# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Collect all arguments for the java command:
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, JAVA_OPTS, and optsEnvironmentVar are not allowed to contain shell fragments,
#     and any embedded shellness will be escaped.
#   * For example: A user cannot expect ${Hostname} to be expanded, as it is an environment variable and will be
#     treated as '${Hostname}' itself on the command line.

set -- \
        "-Dorg.gradle.appname=$APP_BASE_NAME" \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"

# Stop when "xargs" is not available.
if ! command -v xargs >/dev/null 2>&1
then
    die "xargs is not available"
fi

# Use "xargs" to parse quoted args.
#
# With -n1 it outputs one arg per line, with the quotes and backslashes removed.
#
# In Bash we could simply go:
#
#   readarray ARGS < <( xargs -n1 <<<"$var" ) &&
#   set -- "${ARGS[@]}" "$@"
#
# but POSIX shell has neither arrays nor command substitution, so instead we
# post-process each arg (as a line of input to sed) to backslash-escape any
# character that might be a shell metacharacter, then use eval to reverse
# that process (while maintaining the separation between arguments), and wrap
# the whole thing up as a single "set" statement.
#
# This will of course break if any of these variables contains a newline or
# an unmatched quote.
#

eval "set -- $(
        printf '%s\n' "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" |
        xargs -n1 |
        sed ' s~[^-[:alnum:]+,./:=@_]~\\&~g; ' |
        tr '\n' ' '
    )" '"$@"'

exec "$JAVACMD" "$@"
