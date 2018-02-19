     var map;

    function loadResults (data) {
      var items, markers_data = [];
      if (data.venues.length > 0) {
        items = data.venues;

        for (var i = 0; i < items.length; i++) {
          var item = items[i];

          if (item.location.lat != undefined && item.location.lng != undefined) {
            var icon = 'https://foursquare.com/img/categories/food/default.png';

            markers_data.push({
              lat : item.location.lat,
              lng : item.location.lng,
              title : item.name,
              icon : {
                size : new google.maps.Size(32, 32),
                url : icon
              }
            });
          }
        }
      }

      map.addMarkers(markers_data);
    }

    // function printResults(data) {
      // $('#foursquare-results').text(JSON.stringify(data));

    // }

    $(document).on('click', '.pan-to-marker', function(e) {
      e.preventDefault();

      var position, lat, lng, $index;

      $index = $(this).data('marker-index');

      position = map.markers[$index].getPosition();

      lat = position.lat();
      lng = position.lng();

      map.setCenter(lat, lng);
    });
 

	$(document).ready(function() {
				// $("body").bind("ajaxSend", function(elm, xhr, s){
					// if (s.type == "POST") {
							// xhr.setRequestHeader('X-CSRF-Token','nocheck');
					// }
				// });
				//Maps
				map = new GMaps({
					el: '#map',
					lat: -45.043333,
					lng: -67.028333
				});
			
				map.on('marker_added', function (marker) {
					var index = map.markers.indexOf(marker);
		
					if (index == map.markers.length - 1) {
						map.fitZoom();
					}
				});
			
				var xhr = $.getJSON('https://coffeemaker.herokuapp.com/foursquare.json?q[near]=Lima,%20PE&q[query]=Ceviche');
			
				// xhr.done(printResults);
				xhr.done(loadResults);
				
	
				// validate form on keyup and submit
				var v = jQuery("#basicform").validate({
				rules: {
					uname: {
					required: true,
					minlength: 2,
					maxlength: 16
					},
					uemail: {
					required: true,
					minlength: 2,
					email: true,
					maxlength: 100,
					},
					upass1: {
					required: true,
					minlength: 6,
					maxlength: 15,
					},
					upass2: {
					required: true,
					minlength: 6,
					equalTo: "#upass1",
					}
			
				},
				errorElement: "span",
				errorClass: "help-inline-error",
				});

				$(".category").click(function(){
					var images = $("img"); 
					console.log(images);
					console.log($('.category').not(this));
					$('.category').children("img").addClass("highlight");
					$('.category').not(this).children("img").removeClass("highlight");
					});
	
				function logResults(json){
					 var items = [];
					$.each( data, function( key, val ) {
					items.push( "<li id='" + key + "'>" + val + "</li>" );
					});
 
					$( "<ul/>", {
								"class": "my-new-list",
								html: items.join( "" )
					}).appendTo( "body" );
				}
	
				$(".open1").click(function() {
				if (v.form()) {
					$(".frm").hide("fast");
					var category = $(".highlight").attr("id");

					if( category == "category9"){
						$("#usubsector").show("fast");
						$("#uformprof").hide("fast");
						$("#sf2").show("slow");
					} else{
						$("#uformprof").show("fast");
						var url = "http://demo.bluulink.com/api/v1/professions/" + category.substr(category.length - 1);
						$.getJSON( url, function( data ) {
									var items = [];
									$.each( data, function( i, item ) {
										console.log("launched");
										items.push( "<option id='" + item.id + "'>" + item.name + "</option>" );
									});
									console.log(items);
									$( "<select>", {
										"class": "form-control ",
										"id": "uprofesion",
										"name": "uprofesion",
										html: items.join( "" )
									}).appendTo( "#uprofesionselector" );
						});
						$("#sf2").show("slow");
					}
				}
				});
			
				$(".open2").click(function() {
				if (v.form()) {
					$(".frm").hide("fast");
					$("#sf3").show("slow");
					
					$('#target').html('sending..');

					var person = {
						name : "John",
						surname: "John",
						email: "dfasdfa@fasdfa.com",
						language: "ES",
						longitude: "60.90",
						latitude: "45.97",
						id_sector: "1",
						type_user: "WORKER",
						id_profession: "123", 
						id_subsector: "2",
						CSRF: "nocheck"
					}
					
					$.ajax({
						url: 'http://demo.bluulink.com/api/v1/users',
						type: "POST",
						dataType: 'jsonp',
						contentType : 'application/json; charset=utf-8',
						// xhrFields: {
							// withCredentials: true
						// },
						data: JSON.stringify(person),
						success: function (data) {
							$('#target').html(data.msg);
						},
					});
										
					$.get("http://ipinfo.io", function (response) {
						var ip = response.ip;
						var country = response.country;
						var latlong = response.loc;
						var urlcountry = "http://demo.bluulink.com/api/v1/total-users/" + country;
						var lat = latlong.substring(0,latlong.indexOf(","));
						var lng = latlong.substring(latlong.indexOf(",")+1);

						var urlworkers = "http://demo.bluulink.com/api/v1/users/workers/:latitude/:longitude";
						var urlcenters = "http://demo.bluulink.com/api/v1/users/centers/:latitude/:longitude";
						var urlcompanies = "http://demo.bluulink.com/api/v1/users/companies/:latitude/:longitude";
						console.log(urlcountry);
						
						$.getJSON(  urlcountry, function( item ) {
									console.log(item.centers);
									console.log(item.companies);
									console.log(item.users);
						});
						
						
										
					}, "jsonp");
				}
				});
				
				$(".open3").click(function() {
				if (v.form()) {
						
					$("#loader").show();
					setTimeout(function(){
					$("#basicform").html('<h2>Thanks for your time.</h2>');
					}, 1000);
					return false;
				}
				});
				
				$(".back2").click(function() {
				$(".frm").hide("fast");
				$("#uprofesionselector").empty();
				
				if($(".highlight").attr("id") == "category9"){
					$("#usubsector").hide("fast");
				} 
				$("#sf1").show("slow");
				
				});
			
				$(".back3").click(function() {
				$(".frm").hide("fast");
				$("#sf2").show("slow");
				});
				
				$( "#usubsec" ).change(function() {
					$("#uprofesionselector").empty();
					var url = "http://demo.bluulink.com/api/v1/professions/" +$('#usubsec').find(":selected").attr("id");
					$.getJSON( url, function( data ) {
								var items = [];
								$.each( data, function( i, item ) {
									console.log("launched");
									items.push( "<option id='" + item.id + "'>" + item.name + "</option>" );
								});
								console.log(items);
								$( "<select>", {
									"class": "form-control ",
									"id": "uprofesion",
									"name": "uprofesion",
									html: items.join( "" )
								}).appendTo( "#uprofesionselector" );
								$("#uformprof").show("fast");
					});
					
				});
			
			});