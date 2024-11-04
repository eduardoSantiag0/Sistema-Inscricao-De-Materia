import './App.css';
import Aluno from './components/Aluno';
import Headers from './components/Headers';
import MateriasLista from './components/MateriasLista';

function App() {
  return (
    <div className="App">
      <div className='container'> 
        
        <Headers/>
        
        <Aluno/>
        
        <MateriasLista/>

      </div>
    </div>
  );
}

export default App;
