import '../App.css';
import React, { useEffect, useState } from 'react';

const baseURL = "http://localhost:8080"

export default function MateriaInspect (nomeMateria) {

    const [materiaSelecionada, setMateriaSelecionada] = useState([]);

    useEffect(() => {
        fetch (baseURL + 'materias/'+ nomeMateria)
            .then(resp => resp.json())
            .then (data => setMateriaSelecionada (data))
            .then (e => console.log('Matéria não encontrada')); 
    }, []);

    return ( 
    <div>
        <h1> </h1>
         
        


    </div>
    )
} 