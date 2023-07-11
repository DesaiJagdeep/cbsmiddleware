import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICropMaster, NewCropMaster } from '../crop-master.model';

export type PartialUpdateCropMaster = Partial<ICropMaster> & Pick<ICropMaster, 'id'>;

export type EntityResponseType = HttpResponse<ICropMaster>;
export type EntityArrayResponseType = HttpResponse<ICropMaster[]>;

@Injectable({ providedIn: 'root' })
export class CropMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crop-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cropMaster: NewCropMaster): Observable<EntityResponseType> {
    return this.http.post<ICropMaster>(this.resourceUrl, cropMaster, { observe: 'response' });
  }

  update(cropMaster: ICropMaster): Observable<EntityResponseType> {
    return this.http.put<ICropMaster>(`${this.resourceUrl}/${this.getCropMasterIdentifier(cropMaster)}`, cropMaster, {
      observe: 'response',
    });
  }

  partialUpdate(cropMaster: PartialUpdateCropMaster): Observable<EntityResponseType> {
    return this.http.patch<ICropMaster>(`${this.resourceUrl}/${this.getCropMasterIdentifier(cropMaster)}`, cropMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICropMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICropMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCropMasterIdentifier(cropMaster: Pick<ICropMaster, 'id'>): number {
    return cropMaster.id;
  }

  compareCropMaster(o1: Pick<ICropMaster, 'id'> | null, o2: Pick<ICropMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCropMasterIdentifier(o1) === this.getCropMasterIdentifier(o2) : o1 === o2;
  }

  addCropMasterToCollectionIfMissing<Type extends Pick<ICropMaster, 'id'>>(
    cropMasterCollection: Type[],
    ...cropMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cropMasters: Type[] = cropMastersToCheck.filter(isPresent);
    if (cropMasters.length > 0) {
      const cropMasterCollectionIdentifiers = cropMasterCollection.map(cropMasterItem => this.getCropMasterIdentifier(cropMasterItem)!);
      const cropMastersToAdd = cropMasters.filter(cropMasterItem => {
        const cropMasterIdentifier = this.getCropMasterIdentifier(cropMasterItem);
        if (cropMasterCollectionIdentifiers.includes(cropMasterIdentifier)) {
          return false;
        }
        cropMasterCollectionIdentifiers.push(cropMasterIdentifier);
        return true;
      });
      return [...cropMastersToAdd, ...cropMasterCollection];
    }
    return cropMasterCollection;
  }
}
