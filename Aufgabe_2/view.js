/**
 * @author Manuel Adams
 * @since 2018-10-23
 */

document.addEventListener('DOMContentLoaded', initView);

class View {

    constructor(controller) {
        this.controller = controller;
    }

    static displayCounter(currentState) {
        document.getElementById("counter").innerText = currentState;
    }

    populateButtons(nums) {
        let btns = document.getElementsByTagName("button");
        for (let i = 0; i < nums.length; i++) {
            btns[i].addEventListener("click", this.controller.handleMove);
            btns[i].textContent = nums[i];
            if (btns[i].textContent == "0") {
                btns[i].style.visibility = "hidden";
            }
        }
    }
}

function initView() {
    let logic = new Model();
    let controller = new Controller(logic, this);
    let view = new View(controller);

    view.populateButtons(controller.getNumbers());
}
