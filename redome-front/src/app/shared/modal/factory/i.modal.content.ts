import { IModalComponent } from "./i.modal.component";

export interface IModalContent {
    target: IModalComponent; 
    data: any;
    close: (target: IModalComponent) => void; 
}