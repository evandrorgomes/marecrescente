import { Directive, ElementRef, HostListener, Input, OnInit } from '@angular/core';

@Directive({
    selector: '[Filtro]'
})
export class Filtro implements OnInit {


    private typeMap = {
        onlyNumber: {
            padrao: /^[0-9]/,
            paste: /[^0-9]/g
        },
        default: {
            padrao: "",
            paste: "",
        }
    }    

    private map: {};

    //@Input() Filtro: string ;
    @Input() padrao: string;

    constructor(private el: ElementRef) {

    }

    ngOnInit(): void {
        this.map = this.padrao ? this.typeMap[this.padrao] : this.typeMap['default'];
    }

    

    @HostListener('paste', ['$event'])
    onPast(event) {
        var _this = this;
        setTimeout(function () {
            if (_this.map && _this.map['paste'] != '') {
                let reg = new RegExp(_this.map['paste']);
                let value: string = _this.el.nativeElement.value;
                value = value.replace(reg, "");
                _this.el.nativeElement.value = value;
            }
        }, 1);
        ;

    }

    

    @HostListener('keydown', ['$event'])
    onKeyDown(event) {
        let e = <KeyboardEvent>event;
        if (this.map && this.map['padrao'] != '') {
            if ([46, 8, 9, 27, 13].indexOf(e.keyCode) !== -1 ||
                // Allow: Ctrl+A
                (e.keyCode == 65 && e.ctrlKey === true) ||
                // Allow: Ctrl+C
                (e.keyCode == 67 && e.ctrlKey === true) ||
                // Allow: Ctrl+V
                (e.keyCode == 86 && e.ctrlKey === true) ||
                // Allow: Ctrl+X
                (e.keyCode == 88 && e.ctrlKey === true) ||
                // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                // let it happen, don't do anything
                return;
            }
            let keyCode = e.keyCode || e.which;
            if (keyCode >= 96 && keyCode <= 105) {
                // Numpad keys
                keyCode -= 48;
            }
            let ch = String.fromCharCode(keyCode);
            let regEx =  new RegExp(this.map['padrao']);    
            if(regEx.test(ch)) {
                return true;
            }
            else {
                e.preventDefault();
            }
        }
    }


}