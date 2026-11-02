import { questions } from "./question.js";

const startMenu = document.getElementById('startMenu');
const startBtn = document.getElementById('start');

const quizContainer = document.getElementById("quizContainer");
const question = document.getElementById('question');
const optionContainer = document.getElementById('options');
const nextBtn =  document.getElementById('next');
const counterEl = document.getElementById('counter');

const resultContainer = document.getElementById("result");
const scoreElement = document.getElementById('score');
const highScoreBoard =document.querySelectorAll('.highScore');
const restartBtn =  document.getElementById('restart');
const menuBtn = document.querySelectorAll('.menu');
const hiddenClass = 'hidden';
const optionClass = 'option';

let currentQuestion = 0;
let score = 0;


nextBtn.addEventListener('click',nextQuestion);
restartBtn.addEventListener('click',loadQuestions);
startBtn.addEventListener('click',loadQuestions)
menuBtn.forEach((element)=>element.addEventListener('click',()=>{
    resultContainer.classList.add(hiddenClass);
    quizContainer.classList.add(hiddenClass);
    startMenu.classList.remove(hiddenClass);
}))

resultContainer.classList.add(hiddenClass);
quizContainer.classList.add(hiddenClass);
startMenu.classList.remove(hiddenClass);
getHighScore();

function loadQuestions(){
    currentQuestion=0;
    score = 0;

    resultContainer.classList.add(hiddenClass);
    startMenu.classList.add(hiddenClass);
    quizContainer.classList.remove(hiddenClass);
    getQuestion(currentQuestion);
}

function nextQuestion(){
    if(currentQuestion<questions.length-1){
    currentQuestion+=1; 

    getQuestion(currentQuestion)
    }else{
        quizContainer.classList.add(hiddenClass);
        resultContainer.classList.remove(hiddenClass);
        scoreElement.innerText = `You have scored ${score} out of ${questions.length} questions`;

        getHighScore();
    }

}

function getHighScore(){
    const highScore = localStorage.getItem('quizHighScore');
        if(highScore<score) localStorage.setItem('quizHighScore',score);
        
        highScoreBoard.forEach((element) =>{element.innerText = `🏆High Score: ${highScore}`})
        
}

function getQuestion(){
    question.innerText =`${currentQuestion+1}.`+questions[currentQuestion].question;
    
    optionContainer.innerHTML='';
    questions[currentQuestion].options.forEach((option,index) => {
        const optionBtn = document.createElement('button');
        optionBtn.classList.add(optionClass)
        optionBtn.addEventListener('click',()=>{checkAnswer(option,optionBtn)});
        optionBtn.innerText = index+1+': '+option
        optionContainer.append(optionBtn)
    });

    counterEl.innerText = `${currentQuestion+1}/${questions.length}`
    nextBtn.style.display = 'none';
}

function checkAnswer(value,btn){
    const correctAnswer = questions[currentQuestion].answer;
    const optionBtns = document.querySelectorAll('.'+optionClass);
    optionBtns.forEach((optionBtns) => {
        optionBtns.disabled=true;    
    });

    if(value===correctAnswer) {
        score++;
        btn.classList.add('correct');
    }else{
        btn.classList.add('wrong');
    }

    nextBtn.style.display = 'block';
}