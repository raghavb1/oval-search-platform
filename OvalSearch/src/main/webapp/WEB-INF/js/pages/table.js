(function ($) {
    $.fn.dataTableExt.oApi.fnGetColumnData = function (oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
        // check that we have a column id
        if (typeof iColumn == "undefined") return new Array();

        // by default we only wany unique data
        if (typeof bUnique == "undefined") bUnique = true;

        // by default we do want to only look at filtered data
        if (typeof bFiltered == "undefined") bFiltered = true;

        // by default we do not wany to include empty values
        if (typeof bIgnoreEmpty == "undefined") bIgnoreEmpty = true;

        // list of rows which we're going to loop through
        var aiRows;

        // use only filtered rows
        if (bFiltered == true) aiRows = oSettings.aiDisplay;
        // use all rows
        else aiRows = oSettings.aiDisplayMaster; // all row numbers

        // set up data array
        var asResultData = new Array();

        for (var i = 0, c = aiRows.length; i < c; i++) {
            iRow = aiRows[i];
            var aData = this.fnGetData(iRow);
            var sValue = aData[iColumn];

            // ignore empty values?
            if (bIgnoreEmpty == true && sValue.length == 0) continue;

            // ignore unique values?
            else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1) continue;

            // else push the value onto the result data array
            else asResultData.push(sValue);
        }

        return asResultData;
    }
}(jQuery));


function fnCreateSelect(aData) {
    var r = '<select><option value=""></option>', i, iLen = aData.length;
    for (i = 0; i < iLen; i++) {
        r += '<option value="' + aData[i] + '">' + aData[i] + '</option>';
    }
    return r + '</select>';
}

function selector(table, col) {

    var oTable = table.dataTable({
        "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-12'i><'col-lg-12 center'p>>",
        "sPaginationType": "bootstrap",
        "oLanguage": {
            "sSearch": "Search all columns:",
            "sLengthMenu": "_MENU_ records per page"
        },
        "iDisplayLength": 50
    });

    var c = 1;
//    var cols = String(col).split(",");

    table.find("thead th").each(function (i) {
        if (c == col) {
            this.innerHTML = this.innerHTML + '<br>' + fnCreateSelect(oTable.fnGetColumnData(i));
            $('select', this).change(function () {
                oTable.fnFilter($(this).val(), i);
            });
        }
        c++;
    });

    var target = table.find("thead th select");
    target.val(target.find("option:nth-child(2)").val()).trigger('change');

}

$(document).ready(function () {

    //selector('simple-table', 0);

    $(".changer-table").each(function () {
        selector($(this), $(this).attr('data-col'));
    });


});