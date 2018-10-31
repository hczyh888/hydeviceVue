var myjs={}
myjs.formatDate =function(date,f) {
    var curr_date = date.getDate();
    var curr_month = date.getMonth() + 1;
    var curr_year = date.getFullYear();
    String(curr_month).length < 2 ? (curr_month = "0" + curr_month) : curr_month;
    String(curr_date).length < 2 ? (curr_date = "0" + curr_date) : curr_date;
    return [curr_year,curr_month,curr_date].join(f);
}
export {  //添加export抛出模块
    myjs
}