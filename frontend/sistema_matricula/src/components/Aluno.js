import '../App.css';
import React, { useEffect, useState } from 'react';

const baseURL = "http://localhost:8080";

export default function Aluno() {
    const [materias, setMaterias] = useState([]);
    const [formData, setFormData] = useState({
        nomeCompleto: '',
        notaGeral: '',
        anoIngressao: '',
        materiasInscritas: []
    });



    useEffect(() => {
        fetchMaterias();
    }, []);

    const fetchMaterias = async () => {
        try {
            const response = await fetch(baseURL + '/materias/find');
            const data = await response.json();
            setMaterias(Array.isArray(data) ? data : []);
        } catch (error) {
            console.error('Error ao buscar matérias: ', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleMateriaChange = (e) => {
        const materiaNome = e.target.value;
        const isChecked = e.target.checked;

        setFormData(prevState => ({
            ...prevState,
            materiasInscritas: isChecked
                ? [...prevState.materiasInscritas, materiaNome]
                : prevState.materiasInscritas.filter(id => id !== materiaNome)
        }));
    };

    const submitForms = (e) => {
        e.preventDefault();

        const dataToSubmit = {
            nomeCompleto: formData.nomeCompleto,
            notaGeral: parseInt(formData.notaGeral, 10),
            anoIngressao: parseInt(formData.anoIngressao, 10),
            materiasInscritas: formData.materiasInscritas 

        };

        console.log(dataToSubmit);
        
        fetch(baseURL + '/alunos/add', {
            method: 'POST', 
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(dataToSubmit)
        })
        .then(response => {
            if (response.ok) {
                console.log(response);
                return response.json(); 
            } else {
                throw new Error('Failed to add aluno');
            }
        })
        .then(data => {
            alert("Novo aluno adicionado:", data);
        })
        .catch(error => {
            console.error('Error ao adicionar aluno:', error);
            alert("Error ao adicionar aluno");
        });
    }

    return (
        <div className="formsAlunoWrapper">
            <div className="form-group">
                <label>Nome Completo</label>
                <input
                    type="text"
                    name="nomeCompleto"
                    value={formData.nomeCompleto}
                    onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <label>Nota Geral</label>
                <input
                    type="text"
                    name="notaGeral"
                    value={formData.notaGeral}
                    onChange={handleChange}
                />
            </div>
            <div className="form-group">
                <label>Ano de Ingressão</label>
                <input
                    type="text"
                    name="anoIngressao"
                    value={formData.anoIngressao}
                    onChange={handleChange}
                />
            </div>

            {materias.length > 0 ? materias.map((materia) => (
                <div  className="form-group">
                    <label key={materia.nomeMateria}>
                        <input
                            type="checkbox"
                            name="materiasInscritas"
                            id={materia.nomeMateria}
                            value={materia.nomeMateria}
                            checked={formData.materiasInscritas.includes(materia.nomeMateria)}
                            onChange={handleMateriaChange}
                        />
                        {materia.nomeMateria}
                    </label>
                </div>
            )) : <p>Carregando matérias...</p>}

            <button className='btnSubmit' type="submit" onClick={submitForms}>
                <span className='first-letter-style'>C</span>
                adastrar
            </button>
            <br />
            <p className='loadingAnimation'></p>
        </div>
    );
}
