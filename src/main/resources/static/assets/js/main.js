/*  ---------------------------------------------------
    Template Name: Ogani
    Description:  Ogani eCommerce  HTML Template
    Author: Colorlib
    Author URI: https://colorlib.com
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';

(function ($) {

    /*------------------
        Preloader
    --------------------*/
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");

        /*------------------
            Gallery filter
        --------------------*/
        $('.featured__controls li').on('click', function () {
            $('.featured__controls li').removeClass('active');
            $(this).addClass('active');
        });
        if ($('.featured__filter').length > 0) {
            var containerEl = document.querySelector('.featured__filter');
            var mixer = mixitup(containerEl);
        }
    });

    /*------------------
        Background Set
    --------------------*/
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    //Humberger Menu
    $(".humberger__open").on('click', function () {
        $(".humberger__menu__wrapper").addClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").addClass("active");
        $("body").addClass("over_hid");
    });

    $(".humberger__menu__overlay").on('click', function () {
        $(".humberger__menu__wrapper").removeClass("show__humberger__menu__wrapper");
        $(".humberger__menu__overlay").removeClass("active");
        $("body").removeClass("over_hid");
    });

    /*------------------
		Navigation
	--------------------*/
    $(".mobile-menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    /*-----------------------
        Categories Slider
    ------------------------*/
    $(".categories__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 4,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            0: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 3,
            },

            992: {
                items: 4,
            }
        }
    });


    $('.hero__categories__all').on('click', function () {
        $('.hero__categories ul').slideToggle(400);
    });

    /*--------------------------
        Latest Product Slider
    ----------------------------*/
    $(".latest-product__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: false,
        nav: true,
        navText: ["<span class='fa fa-angle-left'><span/>", "<span class='fa fa-angle-right'><span/>"],
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------------
        Product Discount Slider
    -------------------------------*/
    $(".product__discount__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 3,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        responsive: {

            320: {
                items: 1,
            },

            480: {
                items: 2,
            },

            768: {
                items: 2,
            },

            992: {
                items: 3,
            }
        }
    });

    /*---------------------------------
        Product Details Pic Slider
    ----------------------------------*/
    $(".product__details__pic__slider").owlCarousel({
        loop: true,
        margin: 20,
        items: 4,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*-----------------------
		Price Range Slider
	------------------------ */
    var rangeSlider = $(".price-range"),
        minamount = $("#minamount"),
        maxamount = $("#maxamount"),
        minPrice = rangeSlider.data('min'),
        maxPrice = rangeSlider.data('max');
    rangeSlider.slider({
        range: true,
        min: minPrice,
        max: maxPrice,
        values: [minPrice, maxPrice],
        slide: function (event, ui) {
            minamount.val('$' + ui.values[0]);
            maxamount.val('$' + ui.values[1]);
        }
    });
    minamount.val('$' + rangeSlider.slider("values", 0));
    maxamount.val('$' + rangeSlider.slider("values", 1));

    /*--------------------------
        Select
    ----------------------------*/
    $("select").niceSelect();

    /*------------------
		Single Product
	--------------------*/
    $('.product__details__pic__slider img').on('click', function () {

        var imgurl = $(this).data('imgbigurl');
        var bigImg = $('.product__details__pic__item--large').attr('src');
        if (imgurl != bigImg) {
            $('.product__details__pic__item--large').attr({
                src: imgurl
            });
        }
    });

    /*-------------------
       Quantity change
       --------------------- */
    var proQty = $('.pro-qty');
    proQty.prepend('<span class="dec qtybtn">-</span>');
    proQty.append('<span class="inc qtybtn">+</span>');
    proQty.on('click', '.qtybtn', function () {
        var $button = $(this);
        var oldValue = $button.parent().find('input').val();
        if ($button.hasClass('inc')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        $button.parent().find('input').val(newVal);
    });
})(jQuery);


        /*-------------------
        Add item into cart
        --------------------- */

        function addCart(id) {
            const quantity = document.getElementById(`quantity-product-${id}`).value;

            const url = `/cart/?id=${id}&quantity=${quantity}`;
            fetch(url, {
                method: 'POST',
            }).then(function (response) {
                if (response.ok) {
                    alertify.success('Success!!');
                } else {
                    alertify.error('Fail!!');
                }
            });
        }

        /*-------------------
            Update quantity item into cart
            --------------------- */

        function updateItem(id) {
            // Get the updated quantity from the input field
            const quantity = document.getElementById(`quantity-item-${id}`).value;

            //Get price item
            const price = parseFloat(document.getElementById(`price-item-${id}`).innerText);

            const url = `/cart/update?id=${id}&quantity=${quantity}`;
            // Construct the URL for the POST request
            fetch(url, {
                method: 'POST',
            }).then(function (response) {
                if (quantity == 0) {
                    removeItem(id);
                }
                if (response.ok) {
                    const totalPrice = (quantity * price).toFixed(2);
                    document.getElementById(`display-price-${id}`).innerText = totalPrice + ' $';
                    updatedisplayItemCart();
                    alertify.success('Update Success!!');
                } else {
                    alertify.error('Update Fail!!');
                }
            });
        }

        /*-------------------
        Delete item into cart
        --------------------- */

        function removeItem(id) {
            fetch('/cart/' + id, {
                method: 'DELETE',
            }).then(function (response) {
                if (response.ok) {
                    document.getElementById(`remove-item-${id}`).remove();
                    alertify.success('Delete Success!!');
                    updatedisplayItemCart();
                } else {
                    alertify.error('Delete Fail!!');
                }
            });
        }


        function updatedisplayItemCart() {
            const itemRows = document.querySelectorAll(".cart-item");
            let subtotal = 0;
            let totalQuantity = 0;
            itemRows.forEach(function (row){
                const price = parseFloat(row.querySelector(".shoping__cart__price").textContent);
                const quantity = parseInt(row.querySelector(".pro-qty input").value);
                const total = price * quantity;
                subtotal += total;
                totalQuantity += quantity;
            });

            const totalPriceElement = document.getElementById("totalPrice");
            const totalQuantityElement = document.getElementById("totalQuantity");

            totalPriceElement.textContent = "$" + subtotal.toFixed(2);
            totalQuantityElement.textContent = totalQuantity ;
        }