import React, { useState } from 'react'
import { Modal,ModalManager,Effect} from 'react-dynamic-modal';
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
} from 'reactstrap'
import './MyModal.scss';

const MyModal = ({ text, onRequestClose, onConfigChange }) => {
    const [textCopy, setTextCopy] = useState(text)

    var plugins = (textCopy)? Object.keys(textCopy) : textCopy;

    const clearAll = () => {
        setTextCopy(undefined);
        onConfigChange({});
    } 

   return (
        <Modal
            onRequestClose={onRequestClose}
            effect={Effect.FlipVertical3D}>
    
            <Card>
                <CardBody>
                    <CardTitle className="modal-header">Selected Plugins:</CardTitle>
                    <CardSubtitle className="modal-body">
                        { (plugins && plugins.length !== 0)? plugins.map((plugin) => <li key={plugin}>{plugin} - ({textCopy[plugin]})</li>) : "No plugin(s) selected"}
                    </CardSubtitle>
                    <CardSubtitle className="modal-footer">
                        <Button className="button_clearAll" onClick={ clearAll }>Clear All</Button>
                        <Button className="button_close" onClick={ModalManager.close}>Close</Button>
                    </CardSubtitle>
                </CardBody>
            </Card>
        </Modal>
    );
}

export default MyModal
