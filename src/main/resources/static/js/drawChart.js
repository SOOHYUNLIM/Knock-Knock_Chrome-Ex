/* 라인 차트 */
new Chartist.Line('.ct-chart', {
    labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
    series: [
      [12, 9, 7, 8, 5],
      // [2, 1, 3.5, 7, 3],
      // [1, 3, 4, 5, 6]
    ]
  }, {
    fullWidth: true,
    chartPadding: {
      right: 40
    }
  });  


  var data = {
    labels: ['식품', '가전제품', '기타', '서적'],
    series: [20, 15, 40, 10]
  };
  
  var options = {
    labelInterpolationFnc: function(value) {
      return value[0]
    }
  };
  
  var responsiveOptions = [
    ['screen and (min-width: 640px)', {
      chartPadding: 30,
      labelOffset: 100,
      labelDirection: 'explode',
      labelInterpolationFnc: function(value) {
        return value;
      }
    }],
    ['screen and (min-width: 1024px)', {
      labelOffset: 80,
      chartPadding: 20
    }]
  ];
  
  new Chartist.Pie('.ct-Donutchart', data, options, responsiveOptions);

  