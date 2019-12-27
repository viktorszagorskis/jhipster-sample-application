import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBriefingHistory } from 'app/shared/model/briefing-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BriefingHistoryService } from './briefing-history.service';
import { BriefingHistoryDeleteDialogComponent } from './briefing-history-delete-dialog.component';

@Component({
  selector: 'jhi-briefing-history',
  templateUrl: './briefing-history.component.html'
})
export class BriefingHistoryComponent implements OnInit, OnDestroy {
  briefingHistories: IBriefingHistory[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected briefingHistoryService: BriefingHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.briefingHistories = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.briefingHistoryService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBriefingHistory[]>) => this.paginateBriefingHistories(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.briefingHistories = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBriefingHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBriefingHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBriefingHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('briefingHistoryListModification', () => this.reset());
  }

  delete(briefingHistory: IBriefingHistory): void {
    const modalRef = this.modalService.open(BriefingHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.briefingHistory = briefingHistory;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBriefingHistories(data: IBriefingHistory[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.briefingHistories.push(data[i]);
      }
    }
  }
}
