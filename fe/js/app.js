(function (window) {
    'use strict';
    // Your starting point. Enjoy the ride!
    window.onload = function () {
        $(document).ready(function () {
            getTodoList();
            insert_todo();
            filterControl();
            delete_todo();
            deleted_By_Completed();
            toggle(); //update completed
        });
    };

    var counter = {
        count: 0,
        getCount: function () {
            return this.count;
        },
        setCount: function (count) {
            this.count = count;
        },
        subCount: function () {
            return --this.count;
        },
        getComCount: function (completed) {
            return this.count - completed;
        }
    };

    function getTodoList() {
        //Get ALL todo_list
        $('.todo-list').empty();
        $.ajax({
            type: 'GET',
            url: '/api/todos',
            success: function (response) {
                var liClass;
                var checkedLine;

                for (var i = 0; response.length; i++) {
                    if (response[i].completed === 1) {
                        liClass = ' class= "completed" ';
                        checkedLine = ' checked';
                    } else {
                        liClass = "";
                        checkedLine = "";
                    }
                    $('.todo-list').append(
                        "<li id='" + response[i].id + "'" + liClass + ">" +
                        "<div class='view'>" +
                        "<input class='toggle' type='checkbox'" + checkedLine + ">" +
                        "<label>" + response[i].todo + "</label>" +
                        "<button class='destroy'></button> " +
                        "</div>" +
                        "</li>"
                    );
                    $('.todo-count > strong').text($('.todo-list > li').length);
                }
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" +
                    request.responseText + "\n" + "error:" + error);
            }
        });
    }


    //filter

    var filter = {
        show: "all",
        getFilter: function () {
            return this.show;
        },
        setFilter: function (filter) {
            this.show = filter;
        }
    };

    function filterControl() {
        $(document).on('click', 'a', function (event) {
            event.preventDefault();
            var $el;
            var liTag = $('.todo-list > li');
            if (this.id === 'all') {
                liTag.fadeIn(450);
                $('.todo-count > strong').text(liTag.length);
                filter.setFilter("all");
            } else if (this.id === 'completed') {
                $el = $('.' + this.id).fadeIn(450);
                liTag.not($el).hide();
                $('.todo-count > strong').text($el.length);
                filter.setFilter("completed");
                counter.setCount($el.length);
            } else {
                $el = $('.todo-list').children().not('.completed').fadeIn(450);
                liTag.not($el).hide();
                $('.todo-count > strong').text($el.length);
                filter.setFilter("active");
                counter.setCount($el.length);
            }
        });
    }

    //insert AJAX
    function insert_todo() {

        $('.new-todo').on('keypress', function (e) {
            if (e.which === 13) {/* 13 == enter key@ascii */

                var new_todo = $('.new-todo').val();
                if (new_todo === "") {
                    //alert("Input Your plan");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: '/api/todos',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify({
                            todo: new_todo
                        }),
                        success: function (response) {
                            var length = $('.todo-list > li').hasClass("completed").length;
                            if (filter.getFilter() === "completed") {
                                $('.todo-list').prepend(
                                    "<li id='" + response.id + "' style='display: none'>" +
                                    "<div class='view'>" +
                                    "<input class='toggle' type='checkbox'>" +
                                    "<label>" + response.todo + "</label> " +
                                    "<button class='destroy'></button>" +
                                    "</div>" +
                                    "</li>"
                                );
                                $('.todo-count > strong').text(length);

                            } else {
                                $('.todo-list').prepend(
                                    "<li id='" + response.id + "'>" +
                                    "<div class='view'>" +
                                    "<input class='toggle' type='checkbox'>" +
                                    "<label>" + response.todo + "</label> " +
                                    "<button class='destroy'></button>" +
                                    "</div>" +
                                    "</li>"
                                );
                                if(filter.getFilter()==="all"){
                                    $('.todo-count > strong').text($('.todo-list > li').length);

                                }else{
                                    $('.todo-count > strong').text($('.todo-list > li').not('.completed').length);

                                }
                            }

                            $('.new-todo').val("");// Clear User input


                        },
                        error: function (request, status, error) {
                            alert("code:" + request.status + "\n" + "message:" +
                                request.responseText + "\n" + "error:" + error);
                        }
                    });//ajax end
                }//else end
            }
        });
    }

    //Toggle Control
    function toggle() {
        var check = 0;
        //update completed when user click check button
        $(document).on("click", ".toggle", function (event) {
            var li_tag = $(this).parent().parent();
            var li_Id = li_tag.attr('id');
            var completed;
            if ($(this).is(':checked')) {
                completed = 1;
            } else {
                completed = 0;
            }
            // alert("completed : " + completed);
            $.ajax({
                type: 'PUT',
                url: '/api/todos/' + li_Id,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    completed: completed
                }),
                success: function () {

                    switch (filter.getFilter()) {
                        case "completed":

                            if (completed === 1) {
                                $(this).attr("checked");
                                $(li_tag).addClass("completed");
                            } else {
                                $(this).removeAttr("checked");
                                $(li_tag).removeClass("completed").css("display", "none");
                                $('.todo-count > strong').text(counter.subCount());
                            }
                            break;
                        case "active":
                            if (completed === 1) {
                                $(this).attr("checked");
                                $(li_tag).addClass("completed").css("display", "none");
                                $('.todo-count > strong').text(counter.subCount());
                            } else {
                                $(this).removeAttr("checked");
                                $(li_tag).removeClass("completed");
                            }
                            break;
                        default:
                            if (completed === 1) {
                                $(this).attr("checked");
                                $(li_tag).addClass("completed");
                            } else {
                                $(this).removeAttr("checked");
                                $(li_tag).removeClass("completed");
                            }
                    }
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }

            });//ajax end
        });
    }

    //delete Todo
    function delete_todo() {

        $(document).on("click", ".destroy", function (event) {

            var li_tag = $(this).parent().parent();
            var li_Id = li_tag.attr('id');

            $.ajax({
                type: 'DELETE',
                url: 'api/todos/' + li_Id,
                dataType: 'json',
                data: JSON.stringify({
                    id: li_Id
                }),
                success: function () {
                    //$(".todo-count strong").text(--countTodo);
                    $(li_tag).remove();
                    $('.todo-count > strong').text($('.todo-list > li').length);
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }
            });
        });

    }//End Delete Todo

    function deleted_By_Completed() {
        $(document).on("click", '.clear-completed', function () {

            var completed = 1;

            $.ajax({
                type: 'DELETE',
                url: 'api/todos/completed/' + completed,
                dataType: 'json',
                data: JSON.stringify({
                    completed: completed
                }),
                success: function () {
                    $(".completed").remove();
                    $('.todo-count > strong').text($('.todo-list > li').length);
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" +
                        request.responseText + "\n" + "error:" + error);
                }
            });
        });
    }
})(window);
