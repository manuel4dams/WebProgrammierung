/**
 * @author Manuel Adams
 * @since 2018-10-23
 */
const MAX = 9;

class Controller {
    constructor(logic, view) {
        this._logic = logic;
        this._view = view;

        this.logic.numbers = this.getRandomNumbers(9);
    }

    get logic() {
        return this._logic;
    }

    getRandomNumbers(max) {
        let ranNums = [];
        for (let i = 0; i < MAX; i++) {
            let ran = Math.floor(Math.random() * max);
            while (ranNums.includes(ran)) {
                ran = Math.floor(Math.random() * max);
            }
            ranNums[i] = ran;
        }
        return ranNums;
    }

    static isGameWon() {
        let btns = document.getElementsByTagName("button");
        let sorted = true;
        for (let i = 0; i < btns.length; i++) {
            if (parseInt(btns[i].innerText) > parseInt(btns[i + 1].innerText)) {
                console.log("not sorted");
                sorted = false;
                break;
            }

        }
        return sorted;
    }

    static getIndexOfEmpty() {
        var index;
        let btns = document.getElementsByTagName("button");
        for (let i = 0; i < btns.length; i++) {
            if (btns[i].textContent == 0) {
                index = i;
                break;
            } else {
                index = null;
            }
        }
        return index;
    }

    getNumbers() {
        return this.logic.numbers;
    }

    setNumbers(nums) {
        this.logic.numbers = nums;
    }

    getCounter() {
        return this.logic.counter;
    }

    setCounter(cnt) {
        this.logic.counter = cnt;
    }

    handleMove() {
        let index;
        let indexOfEmpty = Controller.getIndexOfEmpty();
        let movable = false;
        let btns = document.getElementsByTagName("button");
        for (let i = 0; i < btns.length; i++) {
            if (this == btns[i]) {
                index = i;
                break;
            } else {
                index = null;
            }
        }
        switch (index) {
            case 0:
                if (indexOfEmpty == 1 || indexOfEmpty == 3) {
                    movable = true;
                }
                break;
            case 1:
                if (indexOfEmpty == 0 || indexOfEmpty == 2 || indexOfEmpty == 4) {
                    movable = true;
                }
                break;
            case 2:
                if (indexOfEmpty == 1 || indexOfEmpty == 5) {
                    movable = true;
                }
                break;
            case 3:
                if (indexOfEmpty == 0 || indexOfEmpty == 6 || indexOfEmpty == 4) {
                    movable = true;
                }
                break;
            case 4:
                if (indexOfEmpty == 1 || indexOfEmpty == 3 || indexOfEmpty == 5 || indexOfEmpty == 7) {
                    movable = true;
                }
                break;
            case 5:
                if (indexOfEmpty == 8 || indexOfEmpty == 2 || indexOfEmpty == 4) {
                    movable = true;
                }
                break;
            case 6:
                if (indexOfEmpty == 7 || indexOfEmpty == 3) {
                    movable = true;
                }
                break;
            case 7:
                if (indexOfEmpty == 6 || indexOfEmpty == 8 || indexOfEmpty == 4) {
                    movable = true;
                }
                break;
            case 8:
                if (indexOfEmpty == 5 || indexOfEmpty == 7) {
                    movable = true;
                }
                break;
            default:
                movable = false;
        }

        if (movable) {
            btns[indexOfEmpty].textContent = btns[index].textContent;
            btns[indexOfEmpty].style.visibility = "visible";
            btns[index].textContent = "0";
            btns[index].style.visibility = "hidden";

            let cnt = document.getElementById("counter").innerText;
            cnt++;

            View.displayCounter(cnt);

            if (Controller.isGameWon()) {
                alert("Game = won!");
            }
        }
    }
}
