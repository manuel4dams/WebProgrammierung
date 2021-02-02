/**
 * @author Manuel Adams
 * @since 2018-10-30
 */
class Model {

    constructor() {
        this._numbers = [];
        this._counter = 0;
    }

    get counter() {
        return this._counter;
    }

    set counter(num) {
        this._counter = num;
    }

    get numbers() {
        return this._numbers;
    }

    set numbers(pNumbers) {
        this._numbers = pNumbers;
    }

    printnumber() {
        console.log(this.numbers);
    }
}
