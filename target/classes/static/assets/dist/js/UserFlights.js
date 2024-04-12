//#region EVENT HANDLERS
$(document).ready(function() {
    $.ajax({
        url: '/UserFlights/LoadFlights',
        type: 'GET',
        data: {},
        success: function(data) {
            CreateTableWithData(data);
        }
    });
});

$(document).on('click', '#UnbookFlight', function () {
    var FlightID = parseInt($(this).val());
    $('#BookingError').prop('hidden', true);

    $.ajax({
        url: `/UserFlights/UnbookFlight/${FlightID}`,
        type: 'GET',
        data: {},
        success: function(data) {
            if (data) {
                window.location.reload();
            }
            else {
                $('#BookingError').prop('hidden', false);
            }
        }
    });
});
//#endregion EVENT HANDLERS

//#region HELPER METHODS
var CreateTableWithData = function (arrTableData) {
    var strTableBody = "";

    arrTableData.forEach(flight => {
        strTableBody += `<tr><td>${flight.destination}</td><td>${flight.origin}</td><td>${flight.departureDate}</td><td>${flight.passengerCount}</td>${TableRowButton(flight.flightID)}</tr>`;
    });

    $('#FlightTableBody').html(strTableBody);
}

var ChangeTable = function (strInputVal) { //modify the resuslts to show only those relaed to the input field
    Array.from($("tbody").find("tr")).forEach(elemTableBodyCell => { // find flight table rows
        var strCurrCell = $(elemTableBodyCell).html().toLowerCase().replaceAll("<td>", "");
        strCurrCell = strCurrCell.replaceAll("</td>", "");

        if (!strCurrCell.includes(strInputVal)) { //if the row has nothing related, hide it
            $(elemTableBodyCell).prop("hidden", true);
        }
        else { //otherwise show it
            $(elemTableBodyCell).prop("hidden", false);
        }
    });
}

var TableRowButton = function (FlightID) {
    return `<td style="width: auto !important;"><button class="btn btn-primary my-2" id="UnbookFlight" value="${FlightID}">Unbook Flight</button></td>`;
}
//#endregion HELPER METHODS