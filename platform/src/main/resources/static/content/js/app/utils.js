function arrayObjectIndexOf(arr, obj){
    for(var i = 0; i < arr.length; i++){

        if(arr[i].id == obj.id){
            return i;
        }
    };
    return -1;
}