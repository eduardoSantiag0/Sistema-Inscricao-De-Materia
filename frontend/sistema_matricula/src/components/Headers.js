import '../App.css';
import logo from '../files/Logo_Retro_White.png' 

export default function Headers() {
    return (
        <>
            <div className='headerIntro'>
                <img className='logo-loop' src={logo}/> 
                <h1> Sistema de Matricula </h1>
            </div>
            <div className='headerIntro'> 
            <p> Bem-vindo estudante! Informe seus dados e se matricule nas aulas. </p>  
            </div>
        
        </>
    );
}