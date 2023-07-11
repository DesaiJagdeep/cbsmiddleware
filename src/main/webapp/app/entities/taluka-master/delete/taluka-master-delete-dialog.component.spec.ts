jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TalukaMasterService } from '../service/taluka-master.service';

import { TalukaMasterDeleteDialogComponent } from './taluka-master-delete-dialog.component';

describe('TalukaMaster Management Delete Component', () => {
  let comp: TalukaMasterDeleteDialogComponent;
  let fixture: ComponentFixture<TalukaMasterDeleteDialogComponent>;
  let service: TalukaMasterService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TalukaMasterDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TalukaMasterDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TalukaMasterDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TalukaMasterService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
