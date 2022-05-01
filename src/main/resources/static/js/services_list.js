$(document).ready(function($) {
    // make table rows clickable
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });

    const table = $('#services-table');
    if (table) {
        if (tableLang === 'en') {
            table.DataTable();
        } else {
            table.DataTable({
                language: {
                    url: tableI18nLocation
                }
            });
        }
    }
});
