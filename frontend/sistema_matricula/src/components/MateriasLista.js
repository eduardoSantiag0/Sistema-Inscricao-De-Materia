import '../App.css';
import React, { useEffect, useState } from 'react';

const baseURL = "http://localhost:8080"

//l
export default function MateriasLista () {
    const [materias, setMaterias] = useState([]);
    
    //Buscar matérias no Banco de dados
    useEffect(() => {
        fetch(baseURL + '/materias/find')
            .then(response => response.json())
            .then(data => setMaterias(data))
            .catch(error => console.error('Error fetching materias:', error));
    }, []);

    return (
        <div className="materiasList">
            <div className= 'como-funciona'> 
                <h2 >Disponibilidade das Matérias </h2>
                
                <p className='tooltip como-funciona'> Como funciona
                    <span className='tooltiptext'> Caso sua nota geral for maior do que algum dos alunos incritos, você ainda pode entrar </span>
                </p>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Nome da matéria</th>
                        <th>Número de vagas</th>
                        <th>Tem vaga?</th>
                    </tr>
                </thead>
                <tbody>
                {materias.map((materia, index) => {
                        return (
                            <tr key={index}>
                                <td>{materia.nomeMateria}</td>
                                <td>{materia.alunosInscritos.length} / {materia.capacidadeMaxima}</td>
                                <td> {materia.alunosInscritos.length >= materia.capacidadeMaxima ? "Não" : "Sim"}  </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            <p className='loadingAnimation loadingAnimation2'></p>
        </div>
    );
}