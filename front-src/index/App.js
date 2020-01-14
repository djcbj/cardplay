import React, { useEffect } from 'react';
import axios from 'axios';
import BattleIndex from '../battle/battleIndex';
import Battle from '../battle/battle';
import Store from '../store/store';
import Bag from '../bag/bag';
import './App.css';

function App() {

  const [battleIndexShow,setBattleIndexShow] = React.useState(false);
  const [storeShow,setStoreShow] = React.useState(false);
  const [bagShow,setBagShow] = React.useState(false);
  const [battleShow,setBattleShow] = React.useState(false);

  const [bag,setBag] = React.useState(null);
  const [money,setMoney] = React.useState(0);
  const [level,setLevel] = React.useState(0);
  const [list,setList] = React.useState(null);
  const [store,setStore] = React.useState(null);
  
  const battleIndexClick = ()=>setBattleIndexShow(true);
  const storeClick = ()=>{
    axios.request({
      url:'adm/store',
      method:'get',
      params:{
        username:'admin'
      }
    }).then(res =>{
      setStore(res.data.bag);
    });
    setStoreShow(true);
  };
  const bagClick = ()=>{
    axios.request({
      url:'adm/backpack',
      method:'get',
      params:{
        username:'admin'
      }
    }).then(res =>{
      console.log(res.data);
      setBag(res.data.bag);
      setMoney(res.data.money);
    });
    setBagShow(true);
  }

  const closeBattleIndex = ()=> setBattleIndexShow(false);
  const closeStore = ()=> setStoreShow(false);
  const closeBag = ()=> setBagShow(false);

  const closeBattle = ()=> setBattleShow(false);

  const battleStart = (id)=> {
    axios.request({
      url:'adm/randomdeck',
      method:'get',
      params:{
          username:'admin',
          level:id
      }
    }).then(res =>{
      setList(res.data.deck);
    });
    setLevel(id);
    setBattleShow(true);
  }

  const sell = (id)=>{
    axios.request({
      url:'adm/sellcard',
      method:'get',
      params:{
        username:'admin',
        cardid:id
    }
    }).then(res =>{
      console.log(res.bag);
      setMoney(res.money);
      setBag(res.bag);
    });
  };

  const buy = (id)=>{
    axios.request({
      url:'adm/buycard',
      method:'get',
      params:{
        username:'admin',
        cardid:id
    }
    }).then(res =>{
      console.log(res.list);
      setList(res.list);
      setMoney(res.money);
      setBag(res.bag);
    });
  };

  useEffect(()=>{
    axios.request({
      url:'adm/backpack',
      method:'get',
      params:{
        id:'admin'
      }
    }).then(res =>{
      setBag(res.data.bag);
      setMoney(res.data.money);
    });
    axios.request({
      url:'adm/store',
      method:'get',
      params:{
        username:'admin'
      }
    }).then(res =>{
      setStore(res.data.bag);
    });
  },[]);

  return (
    <div className="container">
      <h1>卡牌拼点小游戏</h1>
      <button id="battleButton" className="button1 button button-glow button-border button-rounded button-primary" onClick={battleIndexClick}>战斗</button>
      <br />
      <button id="storeButton" className="button1 button button-glow button-border button-rounded button-primary" onClick={storeClick}>商店</button>
      <br />
      <button id="bagButton" className="button1 button button-glow button-border button-rounded button-primary" onClick={bagClick}>背包</button>
      <BattleIndex  show={battleIndexShow} close={closeBattleIndex} start={battleStart} />
      <Store show={storeShow} store={store} money={money} close={closeStore} buy={buy}/>
      <Bag show={bagShow} bag={bag} money={money} close={closeBag} sell={sell} />
      <Battle  show={battleShow} bag={bag} level={level} list={list} close={closeBattle}/>
    </div>
  );
}

export default App;
