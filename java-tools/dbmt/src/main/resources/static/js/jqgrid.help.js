/**
 * @author lichao
 * @version v1.0,2015-10-26
 *
 */

(function ($) {
    window.jqgridHelper = window.jqgridHelper || {};

    jqgridHelper.formateDate = function (cellvalue, options, cell) {
        if (cellvalue != null) {
            var date = new Date(Date.parse(cellvalue.replace(/-/g, "/")));
            return atom.formatDate(date);
        }
        return "";
    };

    //replace icons with FontAwesome icons like above
    jqgridHelper.updatePagerIcons = function (table) {
        var replacement =
        {
            'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    };

    jqgridHelper.getButton = function (grid_selector, data, style, icon, text, event) {
        return "<span class='btn btn-mini " + style + "' onclick=\"$('"
            + grid_selector
            + "').trigger('" + event + "',['" + data + "']);\"><i class='" + icon + "'>&nbsp;" + text + "</i></span>&nbsp;";
    };

    jqgridHelper.getEditButton = function (grid_selector, data) {
        return jqgridHelper.getButton(grid_selector, data, "btn-primary", "fa fa-pencil", "编辑", "grid-edit");
    };

    jqgridHelper.getDeleteButton = function (grid_selector, data) {
        return jqgridHelper.getButton(grid_selector, data, "btn-danger", "fa fa-remove", "删除", "grid-delete");
    };

    jqgridHelper.registerEventHandler = function (target, eventName, callback) {
        var $target;
        if (target instanceof jQuery) {
            $target = target;
        } else {
            $target = $(target);
        }
        $target.off(eventName);
        $target.on(eventName, callback);
    };

    jqgridHelper.registerDeleteEventHandler = function (target, callback) {
        jqgridHelper.registerEventHandler(target, "grid-delete", callback);
    };

    jqgridHelper.registerEditEventHandler = function (target, callback) {
        jqgridHelper.registerEventHandler(target, "grid-edit", callback);
    };

    jqgridHelper.defaultOption = {
        datatype: "json",
        height: '90%',
        jsonReader: {
            root: "list",
            total: "totalPage",
            records: "totalRow"
        },
        rowNum: 10,
        rowList: [10, 20, 50, 100],
        pager: "#grid-pager",
        altRows: true,
        loadComplete: function () {
            var table = this;
            setTimeout(function () {
                jqgridHelper.updatePagerIcons(table);
            }, 0);
        }, autowidth: true
    };
})($);