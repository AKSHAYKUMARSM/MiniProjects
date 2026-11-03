import { questions } from "./question.js";

const randomQuestions = suffleArray([...questions]);

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
const timer = 2.5*60;

let stopwatch;
let currentQuestion = 0;
let score = 0;
let duration = 0;

restartBtn.addEventListener('click',loadQuestions);
startBtn.addEventListener('click',loadQuestions)
menuBtn.forEach((element)=>element.addEventListener('click',()=>{
    clearInterval(stopwatch);
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

    nextBtn.removeEventListener('click',showResult);
    nextBtn.addEventListener('click',nextQuestion);
    nextBtn.textContent = `Next`;
    resultContainer.classList.add(hiddenClass);
    startMenu.classList.add(hiddenClass);
    quizContainer.classList.remove(hiddenClass);
    getQuestion(currentQuestion);

    duration = timer;
    getduration();
    stopwatch = setInterval(()=>{getduration()},1000);
    
}

function getduration(){
    const minute = String(Math.floor(duration/60)).padStart(2,'0');
    const second = String(duration%60).padStart(2,'0');

    document.getElementById('timer').textContent = `Duration: ${minute}:${second}`;
    duration--;

    if(duration<0) {
        clearInterval(stopwatch);
        optionContainer.innerHTML = `<h3>Time's Up</h3>`;
        nextBtn.removeEventListener('click',getQuestion);
        nextBtn.textContent = 'Show Result';
        nextBtn.addEventListener('click',showResult);
        nextBtn.style.display = 'block';
    }
}

function nextQuestion(){
    getQuestion(currentQuestion++);

    if(currentQuestion>=randomQuestions.length-1) {
        nextBtn.removeEventListener('click',getQuestion);
        nextBtn.textContent = 'Show Result';
        nextBtn.addEventListener('click',showResult);
    }

}

function showResult(){
    clearInterval(stopwatch);
    quizContainer.classList.add(hiddenClass);
    resultContainer.classList.remove(hiddenClass);
    scoreElement.innerText = `You have scored ${score} out of ${randomQuestions.length} questions`;

    getHighScore();
}

function getHighScore(){
    let highScore = localStorage.getItem('quizHighScore');
        if(highScore<score) {
            localStorage.setItem('quizHighScore',score);
            highScore = score;
        }
        highScoreBoard.forEach((element) =>{element.innerText = `🏆High Score: ${highScore||0}`})
}

function getQuestion(){
    question.innerText =`${currentQuestion+1}.`+randomQuestions[currentQuestion].question;
    
    optionContainer.innerHTML='';
    randomQuestions[currentQuestion].options.forEach((option,index) => {
        const optionBtn = document.createElement('button');
        optionBtn.classList.add(optionClass)
        optionBtn.addEventListener('click',()=>{checkAnswer(option,optionBtn)});
        optionBtn.innerText = index+1+': '+option
        optionContainer.append(optionBtn)
    });

    counterEl.innerText = `${currentQuestion+1}/${randomQuestions.length}`
    nextBtn.style.display = 'none';
    progressBar();
}

function checkAnswer(value,btn){
    const correctAnswer = randomQuestions[currentQuestion].answer;
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

function progressBar(){
    const progress = document.getElementById('progress');
    const percentage = ((currentQuestion+1)/randomQuestions.length)*100;
    
    progress.style.width = `${percentage}%`
}

function suffleArray(array){
    for (let i = array.length-1; i > 0; i--) {
        let rnd = Math.random()
        let j = Math.floor(rnd*(i+1));
        
        [array[i],array[j]] = [array[j],array[i]];   
    }
    
    return array
}