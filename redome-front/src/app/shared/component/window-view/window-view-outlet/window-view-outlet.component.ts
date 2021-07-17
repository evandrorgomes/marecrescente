import { Component, AfterViewInit, ViewContainerRef, OnDestroy } from '@angular/core';
import { WindowViewService } from '../window-view.service';

@Component({
  selector: 'window-view-outlet',
  template: ''
})
export class WindowViewOutletComponent implements AfterViewInit, OnDestroy {

  constructor(private viewContainerRef: ViewContainerRef,
              private windowView: WindowViewService) {}

  ngAfterViewInit() {
    this.windowView.setOutlet(this.viewContainerRef);
  }

  ngOnDestroy() {
    this.windowView.setOutlet(null);
  }

}
