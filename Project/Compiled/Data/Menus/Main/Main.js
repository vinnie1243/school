function main() {
    test();
    var test1 = 1;
    var test2 = "test";
    var test3 = 2.0;
    var test4 = true;
    const test5 = true;
    if(test4 == false) {
        console.log("if Statement doesnt work");
    } else if(test4 == true) {
        console.log("if Statement works");
    }
    for(var i = 0; i < 10; i++) {
        console.log(i + "for loop");
    }
    var iter = 0;
    while(iter < 10) {
        console.log(iter + "while loop");
        iter++;
    }
}

function test() {
    console.log("test");
}