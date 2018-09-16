/*
jQWidgets v5.4.0 (2017-Oct)
Copyright (c) 2011-2017 jQWidgets.
License: https://jqwidgets.com/license/
*/
/// <reference path="jqwidgets.d.ts" />
import '../jqwidgets/jqxcore.js';
import '../jqwidgets/jqxbuttons.js';
import '../jqwidgets/jqxscrollbar.js';
import { Component, Input, Output, EventEmitter, ElementRef, forwardRef, OnChanges, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';
declare let JQXLite: any;

@Component({
    selector: 'jqxScrollBar',
    template: '<div><ng-content></ng-content></div>'
})

export class jqxScrollBarComponent implements OnChanges
{
   @Input('disabled') attrDisabled: Boolean;
   @Input('largestep') attrLargestep: Number;
   @Input('min') attrMin: Number;
   @Input('max') attrMax: Number;
   @Input('rtl') attrRtl: Boolean;
   @Input('step') attrStep: Number;
   @Input('showButtons') attrShowButtons: Boolean;
   @Input('thumbMinSize') attrThumbMinSize: Number;
   @Input('theme') attrTheme: String;
   @Input('vertical') attrVertical: Boolean;
   @Input('value') attrValue: Number;
   @Input('width') attrWidth: String | Number;
   @Input('height') attrHeight: String | Number;

   @Input('auto-create') autoCreate: boolean = true;

   properties: string[] = ['disabled','height','largestep','min','max','rtl','step','showButtons','thumbMinSize','theme','vertical','value','width'];
   host: any;
   elementRef: ElementRef;
   widgetObject:  jqwidgets.jqxScrollBar;

   constructor(containerElement: ElementRef) {
      this.elementRef = containerElement;
   }

   ngOnInit() {
      if (this.autoCreate) {
         this.createComponent(); 
      }
   }; 

   ngOnChanges(changes: SimpleChanges) {
      if (this.host) {
         for (let i = 0; i < this.properties.length; i++) {
            let attrName = 'attr' + this.properties[i].substring(0, 1).toUpperCase() + this.properties[i].substring(1);
            let areEqual: boolean;

            if (this[attrName] !== undefined) {
               if (typeof this[attrName] === 'object') {
                  if (this[attrName] instanceof Array) {
                     areEqual = this.arraysEqual(this[attrName], this.host.jqxScrollBar(this.properties[i]));
                  }
                  if (areEqual) {
                     return false;
                  }

                  this.host.jqxScrollBar(this.properties[i], this[attrName]);
                  continue;
               }

               if (this[attrName] !== this.host.jqxScrollBar(this.properties[i])) {
                  this.host.jqxScrollBar(this.properties[i], this[attrName]); 
               }
            }
         }
      }
   }

   arraysEqual(attrValue: any, hostValue: any): boolean {
      if (attrValue.length != hostValue.length) {
         return false;
      }
      for (let i = 0; i < attrValue.length; i++) {
         if (attrValue[i] !== hostValue[i]) {
            return false;
         }
      }
      return true;
   }

   manageAttributes(): any {
      let options = {};
      for (let i = 0; i < this.properties.length; i++) {
         let attrName = 'attr' + this.properties[i].substring(0, 1).toUpperCase() + this.properties[i].substring(1);
         if (this[attrName] !== undefined) {
            options[this.properties[i]] = this[attrName];
         }
      }
      return options;
   }

   moveClasses(parentEl: HTMLElement, childEl: HTMLElement): void {
      let classes: any = parentEl.classList;
      if (classes.length > 0) {
        childEl.classList.add(...classes);
      }
      parentEl.className = '';
   }

   moveStyles(parentEl: HTMLElement, childEl: HTMLElement): void {
      let style = parentEl.style.cssText;
      childEl.style.cssText = style
      parentEl.style.cssText = '';
   }

   createComponent(options?: any): void {
      if (options) {
         JQXLite.extend(options, this.manageAttributes());
      }
      else {
        options = this.manageAttributes();
      }
      this.host = JQXLite(this.elementRef.nativeElement.firstChild);

      this.moveClasses(this.elementRef.nativeElement, this.host[0]);
      this.moveStyles(this.elementRef.nativeElement, this.host[0]);

      this.__wireEvents__();
      this.widgetObject = jqwidgets.createInstance(this.host, 'jqxScrollBar', options);

      this.__updateRect__();
   }

   createWidget(options?: any): void {
        this.createComponent(options);
   }

   __updateRect__() : void {
      this.host.css({ width: this.attrWidth, height: this.attrHeight });
   }

   setOptions(options: any) : void {
      this.host.jqxScrollBar('setOptions', options);
   }

   // jqxScrollBarComponent properties
   disabled(arg?: boolean) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('disabled', arg);
      } else {
          return this.host.jqxScrollBar('disabled');
      }
   }

   height(arg?: String | Number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('height', arg);
      } else {
          return this.host.jqxScrollBar('height');
      }
   }

   largestep(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('largestep', arg);
      } else {
          return this.host.jqxScrollBar('largestep');
      }
   }

   min(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('min', arg);
      } else {
          return this.host.jqxScrollBar('min');
      }
   }

   max(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('max', arg);
      } else {
          return this.host.jqxScrollBar('max');
      }
   }

   rtl(arg?: boolean) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('rtl', arg);
      } else {
          return this.host.jqxScrollBar('rtl');
      }
   }

   step(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('step', arg);
      } else {
          return this.host.jqxScrollBar('step');
      }
   }

   showButtons(arg?: boolean) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('showButtons', arg);
      } else {
          return this.host.jqxScrollBar('showButtons');
      }
   }

   thumbMinSize(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('thumbMinSize', arg);
      } else {
          return this.host.jqxScrollBar('thumbMinSize');
      }
   }

   theme(arg?: string) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('theme', arg);
      } else {
          return this.host.jqxScrollBar('theme');
      }
   }

   vertical(arg?: boolean) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('vertical', arg);
      } else {
          return this.host.jqxScrollBar('vertical');
      }
   }

   value(arg?: number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('value', arg);
      } else {
          return this.host.jqxScrollBar('value');
      }
   }

   width(arg?: String | Number) : any {
      if (arg !== undefined) {
          this.host.jqxScrollBar('width', arg);
      } else {
          return this.host.jqxScrollBar('width');
      }
   }


   // jqxScrollBarComponent functions
   destroy(): void {
      this.host.jqxScrollBar('destroy');
   }

   isScrolling(): boolean {
      return this.host.jqxScrollBar('isScrolling');
   }

   setPosition(index: number): void {
      this.host.jqxScrollBar('setPosition', index);
   }


   // jqxScrollBarComponent events
   @Output() onValueChanged = new EventEmitter();

   __wireEvents__(): void {
      this.host.on('valueChanged', (eventData: any) => { this.onValueChanged.emit(eventData); });
   }

} //jqxScrollBarComponent


