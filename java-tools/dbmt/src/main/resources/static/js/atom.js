/* Atom front end library
 * support: lichao@lichao.cn 李超
 * version: v0.1 - 2015.8.5
 * Requires:
 * jQuery v1.6 or later
 * artDialog v6.0.5 | https://github.com/aui/artDialog
 */
(function (factory) {
    //if (typeof define === 'function' && define.amd) {
    //    // AMD标准支持
    //    define(['jquery'], factory);
    //} else if (typeof exports === 'object') {
    //    // CMD标准支持
    //    module.exports = factory(require('jquery'));
    //}
    //传统全局变量方式
    window.atom = factory($);
}(function ($) {
    var _isDebug = false;

    var loading = dialog();

    function showLoading() {
        //Loading展示
        loading.showModal();
    }

    function hideLoading() {
        //Loading结束
        loading.close();
    }

    /**
     * 弹出提示框，将在1.5秒后自动关闭,点击任意位置也可关闭
     * @param content 提示框内容
     */
    function msg(content) {
        var d = dialog({
            content: content,
            quickClose: true
        }).show();
        //setTimeout(function () {
        //    d.close().remove();
        //}, 1500);
    }

    function showDiv(title, content, option) {
        var $option = $.extend({
            title: title,
            content: content,
            width: $(window).width() * 0.8,
            fixed: true,
            okValue: '确定',
            cancelValue: '取消',
            cancel: function () {
            }
        }, option);
        dialog($option).showModal();
    }

    /**
     * 弹出框
     * @param title 弹出框标题，为空则不展示
     * @param content 弹出框内容 url|Jquery Object
     * @param is_iframe 可选参数，是否以iframe方式加载
     * @param option 额外的展示参数
     */
    function show(title, content, is_iframe, option) {
        if (is_iframe) {
            dialog({
                title: title,
                url: content
            }).showModal();
        } else {
            if (content instanceof jQuery) {
                showDiv(title, content, option);
            } else {
                $.ajax({
                    url: content, success: function (data) {
                        showDiv(title, data, option);
                    }, cache: false
                })
            }
        }
    }

    /**
     * 确认框
     * @param content 提醒内容
     * @param callback 点击确认的回调函数
     */
    function confirm(content, callback) {
        var d = dialog({
            title: '提示',
            content: content,
            okValue: '确定',
            ok: function () {
                callback();
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.show();
    }

    function confirmDelete(callback) {
        confirm("确定要删除吗？该操作不可恢复", callback);
    }

    /**
     * 初始化ajax设置
     * @param options
     */
    function initAjaxSetup(options) {
        options = $.extend({
            cache: false, //关闭AJAX相应的缓存
            global: true,
            beforeSend: function (XMLHttpRequest) {
                showLoading()
            },
            success: function (data, textStatus) {
                //请求成功
                //log(arguments);
            },
            complete: function (XMLHttpRequest, textStatus) {
                hideLoading();
            },
            error: function (xhr, textStatus, errorThrown) {
                //请求失败
                //log(arguments);
            }
        }, options);
        $.ajaxSetup(options);
    }

    /**
     * 设置debug模式，为true时打印log输出.
     * atom.debug([true|false]);
     */
    function debug(option) {
        _isDebug = option || true;
    }

    /**
     * 在默认页面加载容器内，加载超链接的指向内容
     * @param alink
     * @returns {boolean}
     */
    function loadPageByHyperlink(alink) {
        var $a_link = $(alink);
        log($a_link.attr('href'));
        atom.loadPage($a_link.attr('href'));
        return false;
    }

    /**
     * 处理后台返回的消息，消息格式为
     * {
     *  code:200|300
     *  message:string
     * }
     * @param data
     * @param callback
     */
    function dealRtnMessage(data, callback) {
        if (data.code == 200) {
            if (callback != null) {
                callback(data);
            } else {
                msg(data.message || '操作成功。', {
                    icon: 1,
                    time: 1500
                });
            }
        } else {
            msg(data.message || '操作失败，请与管理员联系。', {
                icon: 2,
                time: 2000
            });
        }
    }

    /**
     命令模式调用后台

     var command = new atom.command(url);
     command.addParameter("key","value");
     command.addPa.....
     command.execute([function(data,status,jqXHR){....}],[{ajax option}]);
     **/
    function Command(url) {
        this.url = url;
        this.paramsObj = {};
    }

    Command.prototype = {
        //添加请求参数
        addParameter: function (name, value) {
            this.paramsObj[name] = value;
        },
        //直接设置请求数据对象
        setParameters: function (values) {
            this.paramsObj = values;
        },
        execute: function (options) {
            //如果传递了一个function，那么把它当做是success的回调
            if (typeof options == 'function') {
                options = {
                    success: options
                };
            } else if (options === undefined) {
                options = {};
            }
            var that = this;
            options = $.extend(true, {
                url: that.url,
                success: function (data, textStatus) {
                    dealRtnMessage(data);
                },
                type: "post"
            }, options);
            //请求参数转化为param形式
            var q = $.param(that.paramsObj);
            if (options.type.toUpperCase() == 'GET') {
                //如果是GET请求，把设置的参数拼接到地址栏
                console.dir(options);
                console.log(options.url);
                options.url += (options.url.indexOf('?') >= 0 ? '&' : '?') + q;
                options.data = null;
            } else {
                options.data = q;
            }
            $.ajax(options);
        }
    };

    /**
     * 打印日志，只有调用atom.debug()后，才会输出到控制台。
     * atom.log(object|string|int|array|etc...)
     *
     * @param msg
     */
    function log(msg) {
        if (!_isDebug) {
            return;
        }
        if (window.console && window.console.dir) {
            window.console.log("[Atom message:]" + msg);
        } else if (window.opera && window.opera.postError) {
            window.opera.postError(msg);
        }
    }

    var defaultLoadTarget;

    /**
     * 将页面加载到容器中
     * @param target 要加载页面的容器选择符
     * @param url 要加载的url
     * @param callback 加载完成的回调函数
     */
    function loadPage(url, target, callback) {
        var $target;
        if (target) {
            if (target instanceof jQuery) {
                $target = target;
            } else {
                $target = $(target);
            }
        } else {
            $target = defaultLoadTarget;
        }

        var tagName = $target.prop("tagName").toUpperCase();
        if (tagName == "DIV") {
            $target.empty();
            showLoading();
            $target.load(url, function () {
                hideLoading();
                $(".load-page-link").on("click", function () {
                    return atom.loadPageByHyperlink(this);
                });
                callback && callback();
            });

        } else if (tagName == "IFRAME") {
            showLoading();
            $target.unbind(); //清除上一次绑定事件处理程序，否则会继续执行上一次绑定
            $target.load(function () {
                hideLoading();
                callback && callback();
            });
            $target.attr("src", url);
        }
    }

    var storageHelp = {
        getCookie: function (sKey) {
            if (!sKey)
                return "";
            if (document.cookie.length > 0) {
                var startIndex = document.cookie.indexOf(sKey + "=")
                if (startIndex != -1) {
                    startIndex = startIndex + sKey.length + 1
                    var endIndex = document.cookie.indexOf(";", startIndex)
                    if (endIndex == -1) {
                        endIndex = document.cookie.length;
                    }
                    return decodeURIComponent(document.cookie.substring(startIndex, endIndex));
                }
            }
            return ""
        },
        setCookie: function (sKey, sValue, iExpireSeconds) {
            if (!sKey)
                return;
            var expireDate = new Date();
            expireDate.setTime(expireDate.getTime() + iExpireSeconds * 1000);
            document.cookie = sKey + "=" + encodeURIComponent(sValue) +
                ";expires=" + expireDate.toGMTString() + ";";
        },
        deleteCookie: function (sKey) {
            if (!sKey)
                return;
            document.cookie = sKey + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        },
        getStorage: function (sKey) {
            if (!sKey)
                return;
            if (window.localStorage) {
                return decodeURIComponent(localStorage.getItem(sKey));
            }
            else {
                return this.getCookie(sKey);
            }
        },
        setStorage: function (sKey, sValue, iExpireSeconds) {
            if (!sKey)
                return;
            if (window.localStorage) {
                localStorage.setItem(sKey, encodeURIComponent(sValue));
            }
            else {
                this.setCookie(sKey, sValue, iExpireSeconds);
            }
        },
        deleteStorage: function (sKey) {
            if (!sKey)
                return;
            if (window.localStorage) {
                localStorage.removeItem(sKey);
            }
            else {
                this.deleteCookie(sKey);
            }
        }
    };

    /**
     * 时间格式化 返回格式化的时间
     * @param date {object}  可选参数，要格式化的data对象，没有则为当前时间
     * @param format {string} 格式化字符串，例如：'YYYY年MM月DD日 hh时mm分ss秒 星期' 'YYYY/MM/DD week' (中文为星期，英文为week)
     * @return {string} 返回格式化的字符串
     *
     * 例子:
     * formatDate(new Date("january 01,2012"));
     * formatDate(new Date());
     * formatDate('YYYY年MM月DD日 hh时mm分ss秒 星期 YYYY-MM-DD week');
     * formatDate(new Date("january 01,2012"),'YYYY年MM月DD日 hh时mm分ss秒 星期 YYYY/MM/DD week');
     *
     * 格式：
     *    YYYY：4位年,如1993
     *　　YY：2位年,如93
     *　　MM：月份
     *　　DD：日期
     *　　hh：小时
     *　　mm：分钟
     *　　ss：秒钟
     *　　星期：星期，返回如 星期二
     *　　周：返回如 周二
     *　　week：英文星期全称，返回如 Saturday
     *　　www：三位英文星期，返回如 Sat
     */
    function formatDate(date, format) {
        if (!format) {
            format = 'YYYY-MM-DD';
        }
        var week = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', '日', '一', '二', '三', '四', '五', '六'];
        return format.replace(/YYYY|YY|MM|DD|hh|mm|ss|星期|周|www|week/g, function (a) {
            switch (a) {
                case "YYYY":
                    return date.getFullYear();
                case "YY":
                    return (date.getFullYear() + "").slice(2);
                case "MM":
                    return date.getMonth() + 1;
                case "DD":
                    return date.getDate();
                case "hh":
                    return date.getHours();
                case "mm":
                    return date.getMinutes();
                case "ss":
                    return date.getSeconds();
                case "星期":
                    return "星期" + week[date.getDay() + 7];
                case "周":
                    return "周" + week[date.getDay() + 7];
                case "week":
                    return week[date.getDay()];
                case "www":
                    return week[date.getDay()].slice(0, 3);
            }
        });
    }

    /**
     * 初始化页面默认加载位置
     * @param target jQueryObject|jQuery selector string
     */
    function initDefaultLoadTarget(target) {
        if (target instanceof jQuery) {
            defaultLoadTarget = target;
        } else {
            defaultLoadTarget = $(target);
        }
    }

    var bindSelect = function (target, data, textField, dataField, defaultValue) {
        var $target;
        if (target instanceof jQuery) {
            $target = target;
        } else {
            $target = $(target);
        }

        for (var i = 0; i < data.length; i++) {
            var textValue = data[i][textField];
            var dataVlaue = data[i][dataField];
            var selectTag = "";
            if (dataVlaue == defaultValue) {
                selectTag = "selected"
            }
            $target.append("<option " + selectTag + " value='" + dataVlaue + "'>" + textValue + "</option>");
        }
    };

    /**
     * 给所有匹配元素集合赋值，跟据元素的propertyName从entity中取值
     * @param containerId
     *        容器Id
     * @param {} entity
     *     传递过来的java实体Bean对应的js对象
     * @param {} [attrName]
     *     可选参数；
     *     元素的属性名称（例如id，name,tag等）；
     *     默认为tag属性；
     *     元素的该属性值要和entity中的变量名对应
     */
    var setForm = function (containerId, entity, prefix, attrName) {
        if (attrName == null) {   //判断是否传递了第二个参数
            attrName = "name";   //如果没有默认为field属性
        }
        $("#" + containerId + " [" + attrName + "]").each(function (index, element) {
            var tObj = $(this);
            //获取元素attrName属性的属性值
            var propertyVal = tObj.attr(attrName);
            if (propertyVal.indexOf(prefix) == 0) {
                propertyVal = propertyVal.substring(prefix.length)
            }
            //如果实体对象entity中包含名称为propertyVal的变量
            if (entity[propertyVal] != undefined) {
                //获取实体对象entity名称为propertyVal的变量的值
                var enValue = entity[propertyVal];
                if (tObj.is("span")) {
                    tObj.html(enValue); //设置元素的值
                }
                else if (tObj.is("div")) {
                    tObj.html(enValue); //设置元素的值
                }
                else {
                    //如果元素类型是radio或者是checkbox
                    if ("radio" == tObj.attr("type")) {
                        if (enValue == tObj.val()) {
                            tObj.prop("checked", true);
                        }
                        else {
                            tObj.prop("checked", false);
                        }
                    } else if ("checkbox" == tObj.attr("type")) {
                        //如果元素值等于enValue
                        enValue = "," + enValue + ",";
                        if (enValue.indexOf(tObj.val()) > -1) {
                            //设置元素被选中
                            tObj.prop("checked", true);
                        } else {
                            tObj.prop("checked", false);
                        }
                    }
                    else {//其他类型的元素（input：text，hidden，password；textarea等）
                        tObj.val(enValue); //设置元素的值
                    }
                }
            }
        });
    };

    var getForm = function (containerId){
        var $form = $("#"+containerId);
        return JSON.stringify($form.serializeJSON());
    };

    //================初始化==============================
    initAjaxSetup();
    defaultLoadTarget = $("#page-content"); //页面默认加载位置
    //================暴露共有方法=========================
    return {
        initAjaxSetup: initAjaxSetup, //初始化ajax设置
        setDefaultLoadTarget: initDefaultLoadTarget, //初始化页面默认加载位置
        debug: debug, //设置输出控制台日志
        log: log, //打印控制台日志
        Command: Command, //命令方式调用后台
        loadPage: loadPage, //加载页面
        loadPageByHyperlink: loadPageByHyperlink, //在默认页面加载容器内，加载超链接的指向内容
        storageHelp: storageHelp, //前端存储，包括storage和cookie
        formatDate: formatDate, //日期格式化
        msg: msg, //提示
        confirm: confirm, //确认框
        confirmDelete: confirmDelete, //确认删除框
        show: show, //弹出窗口
        dealRtnMessage: dealRtnMessage, //处理返回消息
        getForm: getForm, //获取表单值
        setForm: setForm, //设置表单值
        bindSelect: bindSelect
    }
}));