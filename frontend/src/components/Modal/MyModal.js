import { Hidden } from '@material-ui/core';
import React, { useState } from 'react'
import { Modal,ModalManager,Effect} from 'react-dynamic-modal';
import './MyModal.scss';

const MyModal = ({ text, onRequestClose, onConfigChange }) => {
    const [textCopy, setTextCopy] = useState(text)

    var plugins = (textCopy)? Object.keys(textCopy) : textCopy;

    const clearAll = () => {
        setTextCopy(undefined);
        onConfigChange(new Object());
    } 

   return (
        <Modal
            onRequestClose={onRequestClose}
            effect={Effect.FlipVertical3D}>
    
            <h1 className="modal-header">Selected Plugins:</h1>
            <p className="modal-body" style={{ color: '#000', fontSize: 20, width: 700, height: 200, overflowX: Hidden, overflowY: 'auto', textAlign: 'left' }}>
                { (plugins)? plugins.map((plugin) => <li key={plugin}>{plugin} - ({textCopy[plugin]})</li>) : ""}
            </p>
            <div className="modal-footer">
                <button style={{marginRight: 10, borderRadius: 5}} onClick={ clearAll }>Clear All</button>
                <button style={{borderRadius: 5}} onClick={ModalManager.close}>Close</button>
            </div>
        </Modal>
    );
}

export default MyModal
