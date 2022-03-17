var addCarButton = document.getElementById("add_car_Button");
addCarButton.addEventListener("click",addCar);
var add_car_doc_fragment = document.getElementById("add_car_template").content;
var add_img_doc_fragment = document.getElementById("add_image_template").content;
var car_counter = 0;
var submitCars = document.getElementById("submitCars");
submitCars.disabled = true;

function addCar (evt){
    
    if (car_counter === 3) {
        alert('Add info for those 3 first!');
        addCarButton.disabled = true;
    }
    else{
        car_counter++;
        
        submitCars.disabled = false;

        var carNode = add_car_doc_fragment.cloneNode(true);
        var newImageButton = carNode.querySelector(".add_images_Button");
        var fileInputClose = carNode.querySelector(".fileInputClose");
        var carCloseButton = carNode.querySelector(".carCloseButton");
        var brandSelect = carNode.querySelector(".brand-select");
        var model = carNode.querySelector(".model");
        var description = carNode.querySelector("textarea");
        var firstFileInput = carNode.querySelector(".fileInputDiv").firstElementChild.firstElementChild;
        //add info for server side
        brandSelect.name = "brand"+car_counter;
        model.name = "model"+car_counter;
        description.name = "description"+car_counter;
        firstFileInput.name = "file"+car_counter;
        
        var image_counter=1;

        function addImage(){
            image_counter++;
            var newFileInputDiv = add_img_doc_fragment.cloneNode(true);
            newFileInputDiv.firstElementChild.lastElementChild.addEventListener("click", removeImage);
            
            var newFileInput = newFileInputDiv.firstElementChild.firstElementChild.firstElementChild;
            newFileInput.name = "file"+car_counter;
//            newFileInputDiv.firstElementChild.firstElementChild.fristElementChild.name="file"+car_counter;
            newImageButton.parentElement.parentElement.insertBefore(newFileInputDiv,newImageButton.parentElement);
            
            if(image_counter === 10){
                newImageButton.disabled=true;
                newImageButton.style.cursor = "not-allowed";
//                newImageButton.style.color = "gray";
            }
        }

        function removeImage(event){

            var x_button = event.target;
            if(x_button.tagName === "SPAN"){
                x_button = x_button.parentElement;
            }      
            var fileInputDiv = x_button.parentElement;
            fileInputDiv.parentElement.removeChild(fileInputDiv);
            if (image_counter === 10){
                newImageButton.disabled=false;
                newImageButton.style.cursor = "initial";
//                newImageButton.style.color = "black";
            }
            image_counter--;
        }

        function removeCar(){
            var carFieldSet = carCloseButton.parentElement.parentElement;
            carFieldSet.parentElement.removeChild(carFieldSet);
            if(car_counter === 3 ){
                addCarButton.disabled = false;
            }
            car_counter--;
            if(car_counter === 0){
                submitCars.disabled = true;
            }
        }
        
        function jumpToBrand(evt){
            if(brandSelect.value === "AddNewBrand"){
                brandSelect.value = brandSelect.firstElementChild.value;
                //if myBrandForm is NOT expanded then trigger a click to toogleBrand button to expand the form
                if(!myBrandForm.classList.contains("in")){
                    toogleBrand.click();
                }
                myBrandForm.scrollIntoView();
            }
        }

        newImageButton.addEventListener("click",addImage);
        fileInputClose.addEventListener("click", removeImage);
        carCloseButton.addEventListener("click", removeCar);
        brandSelect.addEventListener("change", jumpToBrand);
        addCarButton.parentElement.insertBefore(carNode, addCarButton);
        
    }
}


var carForm = document.getElementById("myCarForm");
carForm.addEventListener("submit", onSubmitCallback);
//submitCars.addEventListener("click", onSubmitCallback);
function onSubmitCallback(evt){
    if(submitCars.previousElementSibling.previousElementSibling === null){
        evt.preventDefault();
        alert("Nothing to submit!");
    }
    else{
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "carNumber";
        hiddenInput.value = ""+car_counter;
        hiddenInput.form = "myCarForm";
        submitCars.parentElement.insertBefore(hiddenInput, submitCars);
    }
}
    



var toogleBrand = document.getElementById("toogleBrand");
var myBrandForm = document.getElementById("myBrandForm");

toogleBrand.addEventListener("click", toogleBrandClickCallback);
function toogleBrandClickCallback(){
    if(myBrandForm.classList.contains("in")){
        toogleBrand.innerHTML = "+ New Brand";     
    }
    else{
//        myBrandForm.classList.add("in");
        toogleBrand.innerHTML = "- New Brand"; 
    } 
    
}